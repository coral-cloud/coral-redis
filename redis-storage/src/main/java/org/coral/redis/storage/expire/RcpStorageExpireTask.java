package org.coral.redis.storage.expire;

import org.coral.redis.storage.StorageClientExpire;
import org.coral.redis.storage.StorageClientString;
import org.coral.redis.storage.entity.data.RcpExpireData;
import org.coral.redis.storage.entity.data.RcpExpireKey;
import org.coral.redis.storage.entity.data.RcpStringKey;
import org.coral.redis.storage.impl.StorageDbFactory;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class RcpStorageExpireTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpStorageExpireTask.class);

	private static AtomicBoolean runExpire = new AtomicBoolean(true);

	@Override
	public void run() {
		while (true) {
			try {
				expireKeys();
			} catch (InterruptedException e) {
				LOGGER.error("expireKeys Exception", e);
			}
		}
	}

	/**
	 * 定期删除过期keys
	 */
	public void expireKeys() throws InterruptedException {
		try {
			RocksIterator rocksIterator = StorageDbFactory.getExpireDb().getRocksDB().newIterator();
			rocksIterator.seekToFirst();
			while (rocksIterator.isValid()) {
				byte[] expireKey = rocksIterator.key();
				byte[] expireValue = rocksIterator.value();
				RcpExpireKey rcpKey = RcpExpireKey.build(expireKey);
				RcpExpireData rcpData = ObjectUtils.toObject(expireValue, RcpExpireData.class);
				if (rcpData.isExpire()) {
					rocksIterator.next();

					LOGGER.info("delete expire key:{} time stamp:{}", rcpKey.getKeyString(), rcpData.getTime());
					deleteData(rcpKey, rcpData);
					if (StorageClientExpire.getInstance().get(rcpKey) != null) {
						StorageClientExpire.getInstance().delete(rcpKey);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("StorageExpireTask Expire Exception:{}", e.getMessage());
		} finally {
			Thread.sleep(30000);
		}
	}

	/**
	 * @param rcpKey
	 * @param rcpExpireData
	 */
	public void deleteData(RcpExpireKey rcpKey, RcpExpireData rcpExpireData) {
		if (rcpExpireData.getRcpType() == null) {
			LOGGER.warn("rcpKey expire Type not Set:{}", rcpKey);
		}
		switch (rcpExpireData.getRcpType()) {
			case STRING:
				RcpStringKey rcpStringKey = RcpStringKey.build(rcpKey.getKey());
				if (StorageClientString.getInstance().get(rcpStringKey) != null) {
					StorageClientString.getInstance().delete(rcpStringKey);
				}
				break;
			case ZSET:
				break;
			default:
				break;
		}
	}

	/**
	 * 启动清理线程
	 */
	public static void start() {
		if (runExpire.compareAndSet(true, false)) {
			Thread thread = new Thread(new RcpStorageExpireTask());
			thread.setDaemon(true);
			thread.start();
		}
	}
}
