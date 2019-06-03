package com.onecoderspace.base.util;

public class OperationCheck {

	/**
	 * Verify that the data owner is the current user
	 * @author yangwk
	 * @time 2017年7月27日 上午9:52:05
	 * @param uid Data owner ID
	 * @return
	 */
	public static boolean isOwnerCurrentUser(int uid){
		if(uid == LoginSessionHelper.getCurrentUserId()){
			return true;
		}
		return false;
	}
	
}
