package org.coral.redis.storage.expire;

import org.coral.redis.storage.entity.data.RcpContent;
import org.coral.redis.storage.entity.data.RcpExpireKey;
import org.coral.redis.storage.entity.data.RcpKey;
import org.coral.redis.storage.storage.impl.RcpBinlogDb;
import org.helium.util.StringUtils;
import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/**
 * @author wuhao
 *
 * @description: CommandHandler
 * @createTime 2021/10/29 22:16:00
 */


public class RcpStorageSynTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpStorageSynTask.class);

	private AtomicBoolean runSyn = new AtomicBoolean(true);
	private byte[] index;
	private BiConsumer<RcpKey, RcpContent> synFun = null;

	public RcpStorageSynTask(byte[] index, BiConsumer<RcpKey, RcpContent> synFun) {
		this.index = index;
		this.synFun = synFun;
	}

	@Override
	public void run() {

		while (runSyn.get()){
			try {
				sysTask();
			} catch (Exception e){
				LOGGER.error("sysTask Exception", e);
			}
		}

	}

	/**
	 * 同步内容
	 */
	public void sysTask() throws InterruptedException {
		try {
			RocksIterator rocksIterator = RcpBinlogDb.getInstance().getRocksDB().newIterator();
			if (index == null || index.length < 1) {
				rocksIterator.seekToFirst();
			} else {
				rocksIterator.seek(index);
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

	public void stopTask(){
		runSyn.set(false);
	}

	public static RcpStorageSynTask start(byte[] index, BiConsumer<RcpKey, RcpContent> synFun) {
		RcpStorageSynTask rcpStorageSynTask = new RcpStorageSynTask(index, synFun);
		Thread thread = new Thread(rcpStorageSynTask);
		thread.setDaemon(true);
		thread.start();
		return rcpStorageSynTask;
	}
	public static void stop(RcpStorageSynTask storageSynTask) {
		storageSynTask.stopTask();
	}
}
