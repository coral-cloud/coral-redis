package org.coral.redis.storage.impl;

import org.coral.redis.storage.utils.FileUtils;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.WriteBufferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			//not setrocks.cache_index_and_filter_blocks 0
			options.setMaxOpenFiles(StorageConfig.getInstance().getMaxOpenFiles());
			options.setCompressionType(CompressionType.LZ4_COMPRESSION);
			//not set #rocks.level0_compress_enabled no
			//not set #rocks.level1_compress_enabled no
			options.setLevelCompactionDynamicLevelBytes(StorageConfig.getInstance().isLevelCompactionDynamicLevelBytes());
			options.setMaxBackgroundCompactions(StorageConfig.getInstance().getMaxBackgroundCompactions());
			options.setMaxWriteBufferNumber(StorageConfig.getInstance().getMaxWriteBufferNumber());
			options.setMinWriteBufferNumberToMerge(StorageConfig.getInstance().getMinWriteBufferNumberToMerge());
			options.setWriteBufferSize(StorageConfig.getInstance().getWriteBufferSize());
			options.setTargetFileSizeBase(StorageConfig.getInstance().getTargetFileSizeBase());
			options.setMaxBytesForLevelBase(StorageConfig.getInstance().getMaxBytesForLevelBase());
			options.setNumLevels(StorageConfig.getInstance().getNumLevels());
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
