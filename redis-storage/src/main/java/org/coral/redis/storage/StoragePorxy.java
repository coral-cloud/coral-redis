package org.coral.redis.storage;

import org.coral.redis.storage.entity.RcpExpireRow;
import org.coral.redis.storage.entity.RcpStringData;
import org.coral.redis.storage.entity.RcpStringKey;
import org.coral.redis.storage.entity.RcpStringRow;

public class StoragePorxy {
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
			RcpExpireRow expireRow = RcpExpireRow.build(key, content, expire);
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
