package org.coral.redis.storage.expire;

import org.coral.redis.storage.impl.StorageDbFactory;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.coral.redis.storage.impl.StorageDbFactory.getExpireDb;

public class StorageExpireTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageExpireTask.class);

	@Override
	public void run() {
		try {
			RocksIterator rocksIterator = StorageDbFactory.getExpireDb().getRocksDB().newIterator();
			rocksIterator.seekToFirst();

			while (true){
				byte[] expireKey = rocksIterator.key();
				while (expireKey != null) {
//					String value = new String(rocksIterator.value());
//					if (Long.parseLong(value) < System.currentTimeMillis()) {
//						rocksIterator.next();
//						System.out.println("delete key:{}" + new String(expireKey));
//						if (StorageDbFactory.getStorageDb().getRocksDB().get(expireKey) != null){
//							StorageDbFactory.getStorageDb().getRocksDB().delete(expireKey);
//						}
//						if (StorageDbFactory.getExpireDb().getRocksDB().get(expireKey) != null){
//							StorageDbFactory.getExpireDb().getRocksDB().delete(expireKey);
//						}
//					}
//					rocksIterator.next();
					expireKey = rocksIterator.key();
				}
				if (expireKey == null){
					Thread.sleep(100);
				}
			}

		} catch (Exception e) {
			LOGGER.error("StorageExpireTask Expire Exception:{}", e.getMessage());
		}
	}
}
