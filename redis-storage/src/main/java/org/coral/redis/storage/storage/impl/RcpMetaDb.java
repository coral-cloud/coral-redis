package org.coral.redis.storage.storage.impl;

import org.coral.redis.storage.entity.meta.MetaKey;
import org.coral.redis.storage.entity.meta.ServerMetaData;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.storage.storage.RocksDbConstants;
import org.coral.redis.storage.storage.RocksDbFactory;
import org.coral.redis.storage.utils.ByteUtils;
import org.helium.perfmon.Stopwatch;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储redis主机信息
 *
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class RcpMetaDb extends RcpBaseDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpMetaDb.class);
	private RocksDB rocksDB = null;

	public RcpMetaDb() {
		this.rocksDB = RocksDbFactory.getRocksDB(RocksDbConstants.META_PATH);
	}

	@Override
	public RocksDB getRocksDB() {
		return rocksDB;
	}

	private static class StorageClientMetaInit {
		private static RcpMetaDb DB = new RcpMetaDb();
	}

	public static RcpMetaDb getInstance() {
		return StorageClientMetaInit.DB;
	}

	/**
	 * setServerMeta
	 *
	 * @param serverMetaData
	 * @return
	 */
	public boolean setServerMeta(ServerMetaData serverMetaData) {
		Stopwatch stopwatch = StorageCounters.getInstance("set-server-meta").getTx().begin();
		try {
			rocksDB.put(MetaKey.META_KEY, serverMetaData.getBytes());
			stopwatch.end();
		} catch (Exception e) {
			LOGGER.error("setServerMeta exception:{}", ByteUtils.byteToString(MetaKey.META_KEY));
			stopwatch.fail(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * getServerMeta
	 *
	 * @return
	 */
	public ServerMetaData getServerMeta() {
		Stopwatch stopwatch = StorageCounters.getInstance("get-server-meta").getTx().begin();
		try {
			byte[] content = rocksDB.get(MetaKey.META_KEY);
			if (content == null) {
				stopwatch.end();
				return null;
			}
			ServerMetaData serverMetaData = ObjectUtils.toObject(content, ServerMetaData.class);
			stopwatch.end();
			return serverMetaData;
		} catch (Exception e) {
			LOGGER.error("set exception:{}", ByteUtils.byteToString(MetaKey.META_KEY));
			stopwatch.fail(e.getMessage());
		}
		return null;
	}

}
