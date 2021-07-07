package org.coral.redis.storage.test.rocksdb;

import org.coral.redis.storage.impl.StorageRocksDbImpl;
import org.rocksdb.RocksDBException;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-07-02 15:12:00
 */
public class RocksDbPrefmonTest {
	public static void main(String[] args) throws Exception {
		testcache(10000, 16);
	}

	public static void testcache(int count, int dataSize) throws RocksDBException {
		StorageRocksDbImpl storageRocksDb = new StorageRocksDbImpl("./rocks_test/");
		long cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			storageRocksDb.set((i + "").getBytes(StandardCharsets.UTF_8), new byte[dataSize]);
		}
		System.out.println("set " + (System.currentTimeMillis() - cur));
		cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			storageRocksDb.get((i + "").getBytes(StandardCharsets.UTF_8));
		}
		System.out.println("get " + (System.currentTimeMillis() - cur));
	}
}
