package org.turkey.test;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.nio.charset.StandardCharsets;

public class RocksDBExample {

	private static final String DB_PATH = "./rocksdb-data/";
	private static final String CF_DB_PATH = "./rocksdb-data-cf/";
	private static RocksDB rocksDB;

	static {
		RocksDB.loadLibrary();
	}

	public RocksDBExample() throws RocksDBException {
		Options options = new Options();
		options.setCreateIfMissing(true);
		rocksDB = RocksDB.open(options, DB_PATH);
	}

	public void set(String key, String value) {
		set(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
	}

	public void set(byte[] key, byte[] value) {
		try {
			rocksDB.put(key, value);
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
	}

	public byte[] get(String key) {
		return get(key.getBytes(StandardCharsets.UTF_8));
	}

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
		RocksDBExample rocksDBExample = new RocksDBExample();
		long cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			rocksDBExample.set((i + "").getBytes(StandardCharsets.UTF_8), new byte[dataSize]);
		}
		System.out.println("set " + (System.currentTimeMillis() - cur));
		cur = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			rocksDBExample.get(i + "");
		}
		System.out.println("get " + (System.currentTimeMillis() - cur));
	}


}