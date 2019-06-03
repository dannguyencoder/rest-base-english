package com.onecoderspace.base.util.lock;

import com.onecoderspace.base.util.lock.impl.JedisLock;

/**
  * Title: Distributed lock acquisition help class
  * @author yangwenkui
  * @version v1.0
  */
public class DistributedLockUtil{

	/**
	 * Get distributed locks
	 * The default acquisition lock 10s timeout, lock expiration time 60s
	 * @author yangwenkui
	 * @time May 6, 2016 1:30:46 PM
	 * @return
	 */
	public static DistributedLock getDistributedLock(String lockKey){
		lockKey = assembleKey(lockKey);
		JedisLock lock = new JedisLock(lockKey);
		return lock;
	}

	/**
	 * When the formal environment and test environment share a redis, avoid the same key impact
	 * @author yangwenkui
	 * @param lockKey
	 * @return
	 */
	private static String assembleKey(String lockKey) {
		return String.format("lock_%s",lockKey	);
	}

	/**
	 * Get distributed locks
	 * The default acquisition lock 10s timeout, lock expiration time 60s
	 * @author yangwenkui
	 * @time May 6, 2016 1:38:32 PM
	 * @param lockKey
	 * @param timeoutMsecs specifies the lock timeout time
	 * @return
	 */
	public static DistributedLock getDistributedLock(String lockKey,int timeoutMsecs){
		lockKey = assembleKey(lockKey);
		JedisLock lock = new JedisLock(lockKey,timeoutMsecs);
		return lock;
	}

	/**
	 * Get distributed locks
	 * The default acquisition lock 10s timeout, lock expiration time 60s
	 * @author yangwenkui
	 * @time May 6, 2016 1:40:04 PM
	 * @param lockKey lock key
	 * @param timeoutMsecs specifies the lock timeout time
	 * @param expireMsecs specifies the lock expiration time
	 * @return
	 */
	public static DistributedLock getDistributedLock(String lockKey,int timeoutMsecs,int expireMsecs){
		lockKey = assembleKey(lockKey);
		JedisLock lock = new JedisLock(lockKey,expireMsecs,timeoutMsecs);
		return lock;
	}
	
}
