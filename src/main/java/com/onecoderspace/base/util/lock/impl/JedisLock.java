package com.onecoderspace.base.util.lock.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.onecoderspace.base.util.SpringContextUtil;
import com.onecoderspace.base.util.lock.DistributedLock;

/**
  * Title: Distributed lock implementation based on redis--mutual lock
  * @author yangwenkui
  * @version v1.0
  * @time May 6, 2016 2:19:28 PM
  */
public class JedisLock implements DistributedLock{
	
	private static Logger logger = LoggerFactory.getLogger(JedisLock.class);
	
	private static StringRedisTemplate redisTemplate;

    /**
     * Distributed lock key
     */
    String lockKey; //Key value of the lock
    int expireMsecs  = 10 * 1000; //Lock timeout, preventing the thread from waiting indefinitely after entering the lock
    int     timeoutMsecs = 10 * 1000; //Lock waiting to prevent thread starvation
    boolean locked = false; //Have you acquired the lock?

	/**
      * Get the lock of the specified key value
      * @param lockKey key value of the lock
      */
    public JedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

	/**
      * Get the lock of the specified key value, and set the lock timeout time
      * @param lockKey key value of the lock
      * @param timeoutMsecs Get lock timeout
      */
    public JedisLock(String lockKey, int timeoutMsecs) {
        this.lockKey = lockKey;
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
	 * Get the lock of the specified key value, and set the lock timeout and lock expiration time
      * @param lockKey key value of the lock
      * @param timeoutMsecs Get lock timeout
      * @param expireMsecs lock expiration time
      */
    public JedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
        this.lockKey = lockKey;
        this.timeoutMsecs = timeoutMsecs;
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    /**
     * 
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public synchronized boolean acquire() {
        int timeout = timeoutMsecs;
        if(redisTemplate == null){
        		redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
		}
        try {
			while (timeout >= 0) {
			    long expires = System.currentTimeMillis() + expireMsecs + 1;
			    String expiresStr = String.valueOf(expires); //Lock expiration time

			    if (redisTemplate.opsForValue().setIfAbsent(lockKey, expiresStr)) {
			        // lock acquired
			        locked = true;
			        return true;
			    }

			    String currentValueStr = redisTemplate.opsForValue().get(lockKey); //Time in redis
			    if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
			        //If the judgment is empty, if it is not empty, if the value is set by another thread, the second condition is judged to be too late.
			        // lock is expired

			        String oldValueStr = redisTemplate.opsForValue().getAndSet(lockKey, expiresStr);
			        //Get the last lock expiration time and set the current lock expiration time,
			        //Only one thread can get the setup time on the previous line, because jedis.getSet is synchronous
			        if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
			            //At this time, multiple threads happen to be here, but only one thread has the same set value as the current value, so he has the right to acquire the lock.
			            // lock acquired
			            locked = true;
			            return true;
			        }
			    }
			    timeout -= 100;
			    Thread.sleep(100);
			}
        } catch (Exception e) {
			logger.error("release lock due to error",e);
		} 
        return false;
    }

    /**
     * Release lock
     */
    public synchronized void release() {
    		if(redisTemplate == null){
         	redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
 		}
		try {
		    if (locked) {
		    		String currentValueStr = redisTemplate.opsForValue().get(lockKey); //Time in redis
		    		//Whether the verification exceeds the validity period. If it is not within the validity period, it means that the current lock has expired and the lock operation cannot be performed.
			    if (currentValueStr != null && Long.parseLong(currentValueStr) > System.currentTimeMillis()) {
			    		redisTemplate.delete(lockKey);
					locked = false;
			    }
		    }
		} catch (Exception e) {
			logger.error("release lock due to error",e);
		}
    }
}
