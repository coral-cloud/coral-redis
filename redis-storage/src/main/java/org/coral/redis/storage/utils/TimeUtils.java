package org.coral.redis.storage.utils;

/**
 *
 * @author wuhao
 * @createTime 2021-07-14 17:07:00
 */
public class TimeUtils {
	public static final int SEC = 1000;
//	public static byte[] getExpire(long expire) {
//		return String.valueOf(expire * SEC + System.currentTimeMillis()).getBytes();
//	}
//
//	public static byte[] getExpireMs(long expire) {
//		return String.valueOf(expire + System.currentTimeMillis()).getBytes();
//	}

	public static long getExpire(long expire) {
		if (expire <= 0){
			return expire;
		}
		return expire * SEC + System.currentTimeMillis();
	}
	public static long getExpireMs(long expire) {
		if (expire <= 0){
			return expire;
		}
		return expire  + System.currentTimeMillis();
	}
}
