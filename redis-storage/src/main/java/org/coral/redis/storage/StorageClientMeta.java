package org.coral.redis.storage;

import org.coral.redis.storage.entity.data.RcpExpireData;
import org.coral.redis.storage.entity.data.RcpExpireKey;
import org.coral.redis.storage.entity.data.RcpExpireRow;
import org.coral.redis.storage.entity.meta.MetaKey;
import org.coral.redis.storage.entity.meta.ServerMetaData;
import org.coral.redis.storage.impl.StorageDbFactory;
import org.coral.redis.storage.perfmon.StorageCounters;
import org.coral.redis.storage.protostuff.ObjectUtils;
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
public class StorageClientMeta extends StorageClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientMeta.class);

	private static class StorageClientMetaInit {
		private static StorageClientMeta DB = new StorageClientMeta();
	}

	public static StorageClientMeta getInstance() {
		return StorageClientMetaInit.DB;
	}

	/**
	 * setServerMeta
	 * @param serverMetaData
	 * @return
	 */
	public boolean setServerMeta(ServerMetaData serverMetaData) {
		Stopwatch stopwatch = StorageCounters.getInstance("set-server-meta").getTx().begin();
		try {
			RocksDB rocksDB = StorageDbFactory.getMetaDb().getRocksDB();
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
			RocksDB rocksDB = StorageDbFactory.getMetaDb().getRocksDB();
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
