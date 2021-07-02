package org.coral.redis.storage.expire;

import org.coral.redis.storage.impl.StorageDbFactory;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class StorageExpireTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageExpireTask.class);

	private static AtomicBoolean runExpire = new AtomicBoolean(true);

	@Override
	public void run() {
		while (true) {
			expireKeys();
		}
	}

	/**
	 * 定期删除过期keys
	 */
	public void expireKeys() {
		try {
			RocksIterator rocksIterator = StorageDbFactory.getExpireDb().getRocksDB().newIterator();
			rocksIterator.seekToFirst();
			while (rocksIterator.isValid()) {
				byte[] expireKey = rocksIterator.key();
				String value = new String(rocksIterator.value());
				if (Long.parseLong(value) < System.currentTimeMillis()) {
					rocksIterator.next();
					String expireKeyStr = new String(expireKey);
					LOGGER.debug("delete expire key:{} time stamp:{}", expireKeyStr, value);
					if (StorageDbFactory.getStorageDb().getRocksDB().get(expireKey) != null) {
						StorageDbFactory.getStorageDb().getRocksDB().delete(expireKey);
					}
					if (StorageDbFactory.getExpireDb().getRocksDB().get(expireKey) != null) {
						StorageDbFactory.getExpireDb().getRocksDB().delete(expireKey);
					}
				}
			}
			Thread.sleep(100);
		} catch (Exception e) {
			LOGGER.error("StorageExpireTask Expire Exception:{}", e.getMessage());
		}

	}

	/**
	 * 启动清理线程
	 *
	 */
	public static void start() {
		if (runExpire.compareAndSet(true, false)) {
			Thread thread = new Thread(new StorageExpireTask());
			thread.setDaemon(true);
			thread.start();
		}
	}
}
