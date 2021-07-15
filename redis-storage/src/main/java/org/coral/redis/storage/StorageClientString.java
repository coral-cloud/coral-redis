package org.coral.redis.storage;

import org.coral.redis.storage.entity.*;
import org.coral.redis.storage.impl.StorageDbFactory;
import org.coral.redis.storage.params.*;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class StorageClientString extends StorageClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientString.class);
	private static class StorageClientStringInit {
		private static StorageClientString DB = new StorageClientString();
	}

	public static StorageClientString getInstance() {
		return StorageClientString.StorageClientStringInit.DB;
	}
	/**
	 * set
	 *
	 * @param rcpStringRow
	 * @return
	 */
	public boolean set(RcpStringRow rcpStringRow){
		Stopwatch stopwatch = StorageCounters.getInstance("set-string").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getStringDb().getRocksDB();
			rocksDB.put(rcpStringRow.getRcpStringKey().getKey(), rcpStringRow.getRcpStringData().getBytes());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpStringRow.getRcpStringKey().getKeyString());
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
	public RcpStringData get(RcpStringKey rcpStringKey){
		Stopwatch stopwatch = StorageCounters.getInstance("get-string").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getStringDb().getRocksDB();
			byte[] content = rocksDB.get(rcpStringKey.getKey());
			if (content == null) {
				stopwatch.end();
				return null;
			}
			RcpStringData rcpStringData = (RcpStringData) ObjectUtils.toObject(content, RcpStringData.class);
			stopwatch.end();
			//快速过期
			if (rcpStringData.isExpire()){
				return null;
			}
			return rcpStringData;
		} catch (Exception e) {
			LOGGER.error("set exception:{}", rcpStringKey.getKeyString());
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
