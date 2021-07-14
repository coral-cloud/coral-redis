package org.coral.redis.storage;

import org.coral.redis.storage.entity.RcpExpireData;
import org.coral.redis.storage.entity.RcpExpireKey;
import org.coral.redis.storage.entity.RcpExpireRow;
import org.coral.redis.storage.entity.RcpStringKey;
import org.coral.redis.storage.impl.StorageDbFactory;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class StorageClientExpire extends StorageClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientExpire.class);

	private static class StorageClientExpireInit {
		private static StorageClientExpire DB = new StorageClientExpire();
	}

	public static StorageClientExpire getInstance() {
		return StorageClientExpireInit.DB;
	}

	/**
	 * set
	 *
	 * @param rcpRow
	 * @return
	 */
	public boolean set(RcpExpireRow rcpRow) {
		Stopwatch stopwatch = StorageCounters.getInstance("set-expire").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getExpireDb().getRocksDB();
			rocksDB.put(rcpRow.getRcpExpireKey().getBytes(), rcpRow.getRcpExpireData().getBytes());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpRow.getRcpExpireKey().getKeyString());
			stopwatch.fail(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * get
	 *
	 * @param rcpKey
	 * @return
	 */
	public RcpExpireData get(RcpExpireKey rcpKey) {
		Stopwatch stopwatch = StorageCounters.getInstance("get-expire").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getExpireDb().getRocksDB();
			byte[] content = rocksDB.get(rcpKey.getKey());
			if (content == null) {
				stopwatch.end();
				return null;
			}
			RcpExpireData rcpExpireRow = (RcpExpireData) ObjectUtils.toObject(content, RcpExpireData.class);
			stopwatch.end();
			return rcpExpireRow;
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpKey.getKeyString());
			stopwatch.fail(e.getMessage());
		}
		return null;
	}
	/**
	 * get
	 *
	 * @param rcpKey
	 * @return
	 */
	public void delete(RcpStringKey rcpKey) {
		Stopwatch stopwatch = StorageCounters.getInstance("delete-expire").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getExpireDb().getRocksDB();
			rocksDB.delete(rcpKey.getKey());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpKey.getKeyString());
			stopwatch.fail(e.getMessage());
		}
	}
}
