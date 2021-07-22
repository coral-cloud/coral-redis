package org.coral.redis.storage;

import org.coral.redis.storage.entity.data.RcpExpireRow;
import org.coral.redis.storage.entity.data.RcpStringData;
import org.coral.redis.storage.entity.data.RcpStringKey;
import org.coral.redis.storage.entity.data.RcpStringRow;
import org.coral.redis.storage.entity.data.RcpType;

public class StorageProxyString {
	/**
	 * 设置过期处理
	 *
	 * @param key
	 * @param content
	 * @param expire
	 * @return
	 */
	public static boolean set(byte[] key, byte content[], long expire) {
		//设置过期
		if (expire > 0) {
			RcpExpireRow expireRow = RcpExpireRow.build(key, expire, RcpType.STRING);
			StorageClientExpire.getInstance().set(expireRow);
		}
		//设置存储
		RcpStringRow rcpStringRow = RcpStringRow.build(key, content, expire);
		StorageClientString.getInstance().set(rcpStringRow);
		return true;
	}

	/**
	 * 获取key
	 *
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		RcpStringData rcpStringData = StorageClientString.getInstance().get(RcpStringKey.build(key));
		if (rcpStringData == null) {
			return null;
		}
		return rcpStringData.getContent();
	}
}
