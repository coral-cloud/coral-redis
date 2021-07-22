package org.coral.redis.storage.impl;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:35:00
 */
public class StorageDbFactory {
	private static final String DB_STRING_PATH = "./rocksdb/dbstring/";
	private static final String DB_ZSET_PATH = "./rocksdb/dbzset/";
	private static final String DB_SET_PATH = "./rocksdb/dbset/";
	private static final String EXPIRE_PATH = "./rocksdb/expire/";
	private static final String META_PATH = "./rocksdb/meta/";

	private static class DbStringInit {
		private static StorageDb DB = new StorageRocksDbImpl(DB_STRING_PATH);
	}


	private static class DbZSetInit {
		private static StorageDb DB = new StorageRocksDbImpl(DB_ZSET_PATH);
	}

	private static class DbSetInit {
		private static StorageDb DB = new StorageRocksDbImpl(DB_SET_PATH);
	}


	private static class ExpireDbInit {
		private static StorageDb DB = new StorageRocksDbImpl(EXPIRE_PATH);
	}

	private static class MetaDbInit {
		private static StorageDb DB = new StorageRocksDbImpl(META_PATH);
	}

	public static StorageDb getStringDb() {
		return DbStringInit.DB;
	}

	public static StorageDb getZSetDb() {
		return DbZSetInit.DB;
	}

	public static StorageDb getSetDb() {
		return DbSetInit.DB;
	}

	public static StorageDb getExpireDb() {

		return ExpireDbInit.DB;
	}

	public static StorageDb getMetaDb() {

		return MetaDbInit.DB;
	}
}
