package org.coral.redis.storage.impl;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:35:00
 */
public class StorageDbFactory {
	private static final String DB_PATH = "./rocksdb/data/";
	private static final String EXPIRE_PATH = "./rocksdb/expire/";

	private static class StorageDbInit {
		private static StorageDb STORAGEDB = new StorageRocksDbImpl(DB_PATH);
	}

	private static class StorageExpireDbInit {
		private static StorageDb STORAGEDB = new StorageRocksDbImpl(EXPIRE_PATH);
	}

	public static StorageDb getStorageDb() {
		return StorageDbInit.STORAGEDB;
	}

	public static StorageDb getExpireDb() {
		return StorageExpireDbInit.STORAGEDB;
	}
}
