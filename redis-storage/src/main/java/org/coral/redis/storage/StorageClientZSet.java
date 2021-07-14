package org.coral.redis.storage;

import org.coral.redis.storage.entity.*;
import org.coral.redis.storage.impl.StorageDbFactory;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class StorageClientZSet extends StorageClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientZSet.class);

	private static class StorageClientStringInit {
		private static StorageClientZSet DB = new StorageClientZSet();
	}

	public static StorageClientZSet getInstance() {
		return StorageClientZSet.StorageClientStringInit.DB;
	}

	/**
	 * set
	 *
	 * @param rcpZSetRow
	 * @return
	 */
	public boolean zadd(RcpZSetRow rcpZSetRow) {
		Stopwatch stopwatch = StorageCounters.getInstance("zset-zadd").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getZSetDb().getRocksDB();
			RcpMetaKey rcpMetaKey = rcpZSetRow.getRcpMetaKey();
			RcpMetaData rcpMetaData = rcpZSetRow.getRcpMetaData();
			byte[] contentMeta = rocksDB.get(rcpMetaKey.getKey());
			if (contentMeta != null) {
				rcpMetaData = (RcpMetaData) ObjectUtils.toObject(contentMeta, RcpMetaData.class);
			}
			byte[] contentScore = rocksDB.get(rcpZSetRow.getRcpZSetMtsKey().getKey());
			if (contentScore == null) {
				rcpMetaData.setSize(rcpMetaData.getSize() + 1);
			}
			//meta
			rocksDB.put(rcpMetaKey.getKey(), rcpMetaData.getBytes());
			rocksDB.put(rcpZSetRow.getRcpZSetMtsKey().getKey(), rcpZSetRow.getRcpZSetMtsData().getBytes());
			rocksDB.put(rcpZSetRow.getRcpZSetStmKey().getKey(), rcpZSetRow.getRcpZSetStmData().getBytes());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpZSetRow.getRcpMetaKey().getKeyString());
			stopwatch.fail(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * get
	 *
	 * @param rcpMetaKey
	 * @return
	 */
	public RcpZSetRow zrange(RcpMetaKey rcpMetaKey, long start, long stop) {
		Stopwatch stopwatch = StorageCounters.getInstance("get-string").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getZSetDb().getRocksDB();
			byte[] content = rocksDB.get(rcpMetaKey.getKey());
			if (content == null) {
				stopwatch.end();
				return null;
			}
			//rocksDB.
			return null;
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpMetaKey.getKeyString());
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
			RocksDB rocksDB = StorageDbFactory.getStringDb().getRocksDB();
			rocksDB.delete(rcpKey.getKey());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpKey.getKeyString());
			stopwatch.fail(e.getMessage());
		}
	}
}
