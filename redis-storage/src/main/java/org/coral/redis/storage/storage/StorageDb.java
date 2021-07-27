package org.coral.redis.storage.storage;

import org.rocksdb.RocksDB;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public interface StorageDb {

	RocksDB getRocksDB();
}
