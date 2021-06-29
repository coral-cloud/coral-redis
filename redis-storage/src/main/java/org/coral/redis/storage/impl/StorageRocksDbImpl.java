package org.coral.redis.storage.impl;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-06-24 18:27:00
 */
public class StorageRocksDbImpl implements StorageDb {
	private static final String DB_PATH = "./rocksdb-data/";
	private static RocksDB rocksDB;


	public StorageRocksDbImpl() {
		try {
			Options options = new Options();
			options.setCreateIfMissing(true);
			rocksDB = RocksDB.open(options, DB_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public void set(String key, String value) {
		set(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public void set(byte[] key, byte[] value) {
		try {
			rocksDB.put(key, value);
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] get(String key) {
		return get(key.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public byte[] get(byte[] key) {
		try {
			return rocksDB.get(key);
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		testcache(10000, 16);
	}

	public static void testcache(int count, int dataSize) throws RocksDBException {
		StorageRocksDbImpl storageRocksDb = new StorageRocksDbImpl();
		long cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			storageRocksDb.set((i + "").getBytes(StandardCharsets.UTF_8), new byte[dataSize]);
		}
		System.out.println("set " + (System.currentTimeMillis() - cur));
		cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			storageRocksDb.get(i + "");
		}
		System.out.println("get " + (System.currentTimeMillis() - cur));
	}
}
