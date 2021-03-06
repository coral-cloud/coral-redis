package org.coral.redis.storage.storage.impl;

import org.coral.redis.storage.entity.data.*;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.storage.storage.RocksDbPathConfig;
import org.coral.redis.storage.storage.RocksDbFactory;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class RcpZSetDb extends RcpBaseDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpZSetDb.class);

	private RocksDB rocksDB = null;

	public RcpZSetDb() {
		this.rocksDB = RocksDbFactory.getRocksDB(RocksDbPathConfig.getInstance().getZSetDb());
	}

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}

	private static class StorageClientStringInit {
		private static RcpZSetDb DB = new RcpZSetDb();
	}

	public static RcpZSetDb getInstance() {
		return RcpZSetDb.StorageClientStringInit.DB;
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
			RcpMetaKey rcpMetaKey = rcpZSetRow.getRcpMetaKey();
			RcpMetaData rcpMetaData = rcpZSetRow.getRcpMetaData();
			byte[] contentMeta = rocksDB.get(rcpMetaKey.getKey());
			if (contentMeta != null) {
				rcpMetaData = ObjectUtils.toObject(contentMeta, RcpMetaData.class);
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
	 * zrange
	 *
	 * @param rcpMetaKey
	 * @param start
	 * @param stop
	 * @return
	 */
	public List<RcpZSetRow> zrange(RcpMetaKey rcpMetaKey, int start, int stop) {
		Stopwatch stopwatch = StorageCounters.getInstance("get-string").getTx().begin();
		List<RcpZSetRow> list = new ArrayList<>();
		try {
			byte[] contentMeta = rocksDB.get(rcpMetaKey.getKey());
			if (contentMeta == null) {
				return list;
			}
			RcpMetaData rcpMetaData = ObjectUtils.toObject(contentMeta, RcpMetaData.class);
			RocksIterator rocksIterator = rocksDB.newIterator();
			RcpZSetStmKey stmKey = RcpZSetStmKey.build(rcpMetaKey.getKey(), 0, 0, null);
			long stopIndex = stop;
			int count = rcpMetaData.getSize();
			int start_index = start >= 0 ? start : count + start;
			int stop_index = stop >= 0 ? stop : count + stop;
			start_index = start_index <= 0 ? 0 : start_index;
			stop_index = stop_index >= count ? count - 1 : stop_index;
			if (start_index > stop_index || start_index >= count || stop_index < 0) {
				return list;
			}
			long curIndex = 0;
			for (rocksIterator.seek(stmKey.getKey()); rocksIterator.isValid() && curIndex <= stopIndex; ++curIndex) {
				if (curIndex >= start_index) {
					byte[] stmKeyBytes = rocksIterator.key();
					RcpZSetStmKey rcpZSetStmKey = RcpZSetStmKey.parse(stmKeyBytes);
					RcpZSetStmData rcpZSetStmData = RcpZSetStmData.build();
					RcpZSetRow rcpZSetRow = new RcpZSetRow();
					rcpZSetRow.setRcpZSetStmKey(rcpZSetStmKey);
					rcpZSetRow.setRcpZSetStmData(rcpZSetStmData);
					rcpZSetRow.setRcpMetaKey(rcpMetaKey);
					rcpZSetRow.setRcpMetaData(rcpMetaData);
					list.add(rcpZSetRow);
				}
				rocksIterator.next();
			}
		} catch (Exception e) {
			LOGGER.error("zrange exception:{}", rcpMetaKey.getKeyString(), e);
			stopwatch.fail(e.getMessage());
		}
		return list;
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
