package org.coral.redis.storage.impl;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:35:00
 */
public class StorageDbFactory {
	private static class StorageDbFactoryInit {
		private static StorageDb STORAGEDB = new StorageRocksDbImpl();
	}

	public static StorageDb getStorageDb() {
		return StorageDbFactoryInit.STORAGEDB;
	}
}
