package org.coral.redis.storage.test;

import org.coral.redis.storage.StorageProxyZSet;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StorageProxyZSetTest {
	public static void main(String[] args) throws InterruptedException {
		StorageProxyZSetTest storageProxyTest = new StorageProxyZSetTest();
		storageProxyTest.testZAddZRange();
	}
	@Test
	public void testZAddZRange() throws InterruptedException {

		byte[] key = "testKey1".getBytes(StandardCharsets.UTF_8);
		Map<byte[], Double> data = new HashMap<>();
		data.put("m1".getBytes(StandardCharsets.UTF_8), 1d);
		data.put("m2".getBytes(StandardCharsets.UTF_8), 2d);
		data.put("m3".getBytes(StandardCharsets.UTF_8), 3d);
		StorageProxyZSet.zadd(key, data);
		StorageProxyZSet.zrange(key, 0, 1);
	}

}
