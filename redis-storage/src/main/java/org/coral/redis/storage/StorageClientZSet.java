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
	public boolean zadd(RcpZSetRow rcpZSetRow){
		Stopwatch stopwatch = StorageCounters.getInstance("zset-zadd").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getZSetDb().getRocksDB();
			RcpMetaKey rcpMetaKey = rcpZSetRow.getRcpMetaKey();
			RcpMetaData rcpMetaData = rcpZSetRow.getRcpMetaData();
			if (rocksDB.get(rcpZSetRow.getRcpMetaKey().getKey()) != null){

			}
			if (rocksDB.get(rcpZSetRow.getRcpZSetMtsKey().getKey()) != null){

			}
			//rocksDB.put(rcpZSetRow.getRcpMetaKey().getBytes(), rcpStringRow.getRcpStringData().getBytes());
			stopwatch.end();
		} catch (Exception e) {
			//LOGGER.error("set exception:{}", rcpStringRow.getRcpStringKey().getKeyString());
			//stopwatch.fail(e.getMessage());
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
	public RcpStringData zrange(RcpStringKey rcpStringKey){
		Stopwatch stopwatch = StorageCounters.getInstance("get-string").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getExpireDb().getRocksDB();
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
