package org.coral.redis.storage.storage.impl;

import org.coral.redis.storage.entity.data.RcpExpireData;
import org.coral.redis.storage.entity.data.RcpExpireKey;
import org.coral.redis.storage.entity.data.RcpExpireRow;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.storage.storage.RocksDbPathConfig;
import org.coral.redis.storage.storage.RocksDbFactory;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class RcpExpireDb extends RcpBaseDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpExpireDb.class);

	private RocksDB rocksDB = null;

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}

	public RcpExpireDb() {
		this.rocksDB = RocksDbFactory.getRocksDB(RocksDbPathConfig.getInstance().getExpireDb());
	}

	private static class StorageClientExpireInit {
		private static RcpExpireDb DB = new RcpExpireDb();
	}

	public static RcpExpireDb getInstance() {
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
			rocksDB.put(rcpRow.getRcpExpireKey().getKey(), rcpRow.getRcpExpireData().getBytes());
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
			byte[] content = rocksDB.get(rcpKey.getKey());
			if (content == null) {
				stopwatch.end();
				return null;
			}
			RcpExpireData rcpExpireRow = ObjectUtils.toObject(content, RcpExpireData.class);
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
	public void delete(RcpExpireKey rcpKey) {
		Stopwatch stopwatch = StorageCounters.getInstance("delete-expire").getTx().begin();
		try {
			rocksDB.delete(rcpKey.getKey());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpKey.getKeyString());
			stopwatch.fail(e.getMessage());
		}
	}
}
