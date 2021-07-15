package org.coral.redis.storage.expire;

import org.coral.redis.storage.StorageClientExpire;
import org.coral.redis.storage.StorageClientString;
import org.coral.redis.storage.entity.RcpExpireData;
import org.coral.redis.storage.entity.RcpExpireKey;
import org.coral.redis.storage.entity.RcpStringKey;
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
//		while (true) {
//			try {
//				expireKeys();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
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
				RcpExpireKey rcpKey = (RcpExpireKey) ObjectUtils.toObject(expireKey, RcpExpireKey.class);
				RcpExpireData rcpData = (RcpExpireData) ObjectUtils.toObject(expireValue, RcpExpireData.class);
				if (rcpData.isExpire()) {
					rocksIterator.next();

					LOGGER.debug("delete expire key:{} time stamp:{}", rcpKey.getKeyString(), rcpData.getTime());
					deleteData(rcpKey, rcpData);
					if (StorageClientExpire.getInstance().get(rcpKey) != null) {
						StorageClientExpire.getInstance().delete(rcpKey);
					}
				}
			}
			Thread.sleep(100);
		} catch (Exception e) {
			LOGGER.error("StorageExpireTask Expire Exception:{}", e.getMessage());

		}
		Thread.sleep(100);

	}

	/**
	 * @param rcpKey
	 * @param rcpExpireData
	 */
	public void deleteData(RcpExpireKey rcpKey, RcpExpireData rcpExpireData) {
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
