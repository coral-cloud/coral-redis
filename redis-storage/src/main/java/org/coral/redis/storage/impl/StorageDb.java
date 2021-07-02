package org.coral.redis.storage.impl;

import org.rocksdb.RocksDB;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public interface StorageDb {


	void set(byte[] key, byte[] value);

	byte[] get(byte[] key);

	RocksDB getRocksDB();
}
