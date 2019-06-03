/**
 * Title:
 * Description:
 * @author yangwenkui
 * @version v1.0
 * @time 2016年5月6日 上午10:52:58
 */
package com.onecoderspace.base.util.lock;

/**
  * Title: Distributed lock interface
  * @author yangwenkui
  * @version v1.0
  * @time May 6, 2016 10:52:58 AM
  */
public interface DistributedLock {

	/**
	 * Get the lock
	 * @author yangwenkui
	 * @time May 6, 2016 11:02:54 AM
	 * @return
	 * @throws InterruptedException
	 */
	public boolean acquire();

	/**
	 * Release lock
	 * @author yangwenkui
	 * @time May 6, 2016 11:02:59 AM
	 */
	public void release();
	
}
