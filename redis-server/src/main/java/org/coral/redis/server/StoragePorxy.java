package org.coral.redis.server;

import org.coral.redis.storage.impl.StorageDbFactory;

public class StoragePorxy {
	/**
	 * 设置过期处理
	 * @param key
	 * @param content
	 * @param expire
	 * @return
	 */
	public static boolean set(byte[] key, byte content[], long expire) {
		//设置过期
		if (expire > 0){
			StorageDbFactory.getExpireDb().set(key,
					String.valueOf(expire * 1000 + System.currentTimeMillis()).getBytes());
		}
		//设置存储
		StorageDbFactory.getStorageDb().set(key, content);
		return true;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key){
		return StorageDbFactory.getStorageDb().get(key);
	}
}
