package org.coral.redis.storage.storage;

import org.coral.redis.storage.utils.FileUtils;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public class RocksDbFactory {

	private static final Map<String, RocksDB> rocksDBHashMap = new ConcurrentHashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(RocksDbFactory.class);

	/**
	 * getRocksDB
	 *
	 * @param path
	 * @return
	 */
	public static RocksDB getRocksDB(String path) {
		RocksDB rocksDB = rocksDBHashMap.get(path);
		if (rocksDB == null) {
			synchronized (RocksDbFactory.class) {
				rocksDB = rocksDBHashMap.get(path);
				if (rocksDB == null) {
					rocksDB = buildDb(path);
				}
			}
			rocksDBHashMap.put(path, rocksDB);
		}
		return rocksDB;
	}

	/**
	 * buildDb
	 *
	 * @param path
	 * @return
	 */
	private static RocksDB buildDb(String path) {
		try {
			FileUtils.checkAndCreateDir(path);
			Options options = new Options();
			options.setCreateIfMissing(true);
			//not setrocks.cache_index_and_filter_blocks 0
			options.setMaxOpenFiles(RocksDbConfig.getInstance().getMaxOpenFiles());
			options.setCompressionType(CompressionType.LZ4_COMPRESSION);
			//not set #rocks.level0_compress_enabled no
			//not set #rocks.level1_compress_enabled no
			options.setLevelCompactionDynamicLevelBytes(RocksDbConfig.getInstance().isLevelCompactionDynamicLevelBytes());
			options.setMaxBackgroundCompactions(RocksDbConfig.getInstance().getMaxBackgroundCompactions());
			options.setMaxWriteBufferNumber(RocksDbConfig.getInstance().getMaxWriteBufferNumber());
			options.setMinWriteBufferNumberToMerge(RocksDbConfig.getInstance().getMinWriteBufferNumberToMerge());
			options.setWriteBufferSize(RocksDbConfig.getInstance().getWriteBufferSize());
			options.setTargetFileSizeBase(RocksDbConfig.getInstance().getTargetFileSizeBase());
			options.setMaxBytesForLevelBase(RocksDbConfig.getInstance().getMaxBytesForLevelBase());
			options.setNumLevels(RocksDbConfig.getInstance().getNumLevels());
			RocksDB rocksDB = RocksDB.open(options, path);
			return rocksDB;
		} catch (Exception e) {
			LOGGER.error("StorageRocksDb Create Exception:{}", path, e);
		}
		return null;
	}

}
