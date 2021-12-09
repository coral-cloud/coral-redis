package org.coral.redis.storage.storage.impl;

import org.coral.redis.storage.entity.data.*;
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
public class RcpStringDb extends RcpBaseDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpStringDb.class);

	private RocksDB rocksDB = null;

	public RcpStringDb() {
		this.rocksDB = RocksDbFactory.getRocksDB(RocksDbPathConfig.getInstance().getStringDb());
	}

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}

	private static class StorageClientStringInit {
		private static RcpStringDb DB = new RcpStringDb();
	}

	public static RcpStringDb getInstance() {
		return RcpStringDb.StorageClientStringInit.DB;
	}

	/**
	 * set
	 *
	 * @param row
	 * @return
	 */
	public boolean set(RcpStringRow row) {
		Stopwatch stopwatch = StorageCounters.getInstance("set-string").getTx().begin();
		try {
			byte[] content = RcpContent.encode(RcpType.STRING, row.getRcpStringData().getBytes());
			rocksDB.put(row.getRcpStringKey().getKey(), content);
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", row.getRcpStringKey().getKeyString(), e);
			stopwatch.fail(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * get
	 *
	 * @param rcpStringKey
	 * @return
	 */
	public RcpStringData get(RcpStringKey rcpStringKey) {
		Stopwatch stopwatch = StorageCounters.getInstance("get-string").getTx().begin();
		try {
			byte[] content = rocksDB.get(rcpStringKey.getKey());
			if (content == null) {
				stopwatch.end();
				return null;
			}
			RcpContent rcpContent = RcpContent.decode(content);
			if (rcpContent.getRcpType() != RcpType.STRING) {
				return null;
			}
			RcpStringData rcpStringData = ObjectUtils.toObject(rcpContent.getContent(), RcpStringData.class);
			stopwatch.end();
			//快速过期
			if (rcpStringData.isExpire()) {
				return null;
			}
			return rcpStringData;
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpStringKey.getKeyString(), e);
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
		Stopwatch stopwatch = StorageCounters.getInstance("delete-string").getTx().begin();
		try {
			rocksDB.delete(rcpKey.getKey());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpKey.getKeyString());
			stopwatch.fail(e.getMessage());
		}
	}
}
