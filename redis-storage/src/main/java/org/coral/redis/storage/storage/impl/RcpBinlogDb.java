package org.coral.redis.storage.storage.impl;

import org.coral.redis.storage.entity.data.*;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.storage.storage.RocksDbFactory;
import org.coral.redis.storage.storage.RocksDbPathConfig;
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
public class RcpBinlogDb extends RcpBaseDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpBinlogDb.class);

	private RocksDB rocksDB = null;

	public RcpBinlogDb() {
		this.rocksDB = RocksDbFactory.getRocksDB(RocksDbPathConfig.getInstance().getBinlogDb());
	}

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}

	private static class StorageClientStringInit {
		private static RcpBinlogDb DB = new RcpBinlogDb();
	}

	public static RcpBinlogDb getInstance() {
		return RcpBinlogDb.StorageClientStringInit.DB;
	}
}
