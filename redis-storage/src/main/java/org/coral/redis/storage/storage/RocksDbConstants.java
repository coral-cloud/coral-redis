package org.coral.redis.storage.storage;

/**
 * @author wuhao
 * @createTime 2021-07-27 11:38:00
 */
public class RocksDbConstants {
	public static final String DB_STRING_PATH = "./rocksdb/dbstring/";
	public static final String DB_HASH_PATH = "./rocksdb/dbhash/";
	public static final String DB_LIST_PATH = "./rocksdb/dblist/";
	public static final String DB_SET_PATH = "./rocksdb/dbset/";
	public static final String DB_ZSET_PATH = "./rocksdb/dbzset/";
	public static final String EXPIRE_PATH = "./rocksdb/expire/";
	public static final String META_PATH = "./rocksdb/meta/";
	public static final String BINLOG_PATH = "./rocksdb/binlog/";
}
