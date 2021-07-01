package org.coral.redis.storage.impl;

import org.rocksdb.RocksDB;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public interface StorageDb {

	void set(String key, String value);

	void set(byte[] key, byte[] value);

	byte[] get(String key);

	byte[] get(byte[] key);

	RocksDB getRocksDB();
}
