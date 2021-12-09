package org.coral.redis.storage.expire;

import org.coral.redis.storage.entity.data.*;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.storage.storage.impl.RcpBinlogDb;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class RcpStorageSynTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpStorageSynTask.class);

	private AtomicBoolean runSyn = new AtomicBoolean(true);
	private long start = 0;
	private BiConsumer<RcpKey, RcpContent> synFun = null;

	public RcpStorageSynTask(long start, BiConsumer<RcpKey, RcpContent> synFun) {
		this.start = start;
		this.synFun = synFun;
	}

	@Override
	public void run() {
		try {
			sysTask();
		} catch (InterruptedException e) {
			LOGGER.error("sysTask Exception", e);
		}
	}

	/**
	 * 同步内容
	 */
	public void sysTask() throws InterruptedException {
		try {
			RocksIterator rocksIterator = RcpBinlogDb.getInstance().getRocksDB().newIterator();
			if (start < 1) {
				rocksIterator.seekToFirst();
			} else {
				//TODO 增量同步
				rocksIterator.seek("".getBytes());
			}

			while (rocksIterator.isValid()) {
				byte[] keyBytes = rocksIterator.key();
				byte[] valueBytes = rocksIterator.value();
				if (keyBytes == null || valueBytes == null) {
					continue;
				}
				RcpKey rcpKey = RcpExpireKey.build(keyBytes);
				RcpContent rcpContent = RcpContent.decode(valueBytes);

				synFun.accept(rcpKey, rcpContent);
				rocksIterator.next();
			}

		} catch (Exception e) {
			LOGGER.error("StorageExpireTask Expire Exception:{}", e.getMessage());
		} finally {
			Thread.sleep(1000);
		}
	}


	public static void start(long start, BiConsumer<RcpKey, RcpContent> synFun) {
		Thread thread = new Thread(new RcpStorageSynTask(start, synFun));
		thread.setDaemon(true);
		thread.start();
	}
}
