package org.coral.redis.storage.impl;

import org.coral.redis.storage.FileUtils;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public class StorageRocksDbImpl implements StorageDb {

	private RocksDB rocksDB;

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageRocksDbImpl.class);

	public StorageRocksDbImpl(String path) {
		try {
			FileUtils.checkAndCreateDir(path);
			Options options = new Options();
			options.setCreateIfMissing(true);
			rocksDB = RocksDB.open(options, path);
		} catch (Exception e) {
			LOGGER.error("StorageRocksDb Create Exception:{}", path, e);
		}

	}

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}


}
