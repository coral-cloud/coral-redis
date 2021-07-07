package org.coral.redis.storage.test;

import org.coral.redis.storage.StoragePorxy;
import org.coral.redis.storage.expire.StorageExpireTask;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageProxyTest {
	@Test
	public void testExpire() throws InterruptedException {
		Thread thread = new Thread(new StorageExpireTask());
		thread.start();
		byte[] key = "testKey".getBytes(StandardCharsets.UTF_8);
		byte[] value = "testValue".getBytes(StandardCharsets.UTF_8);
		byte[] valueNull = null;
		StoragePorxy.set(key, value, 3);
		byte[] valueRet = StoragePorxy.get(key);
		assertArrayEquals(value, valueRet);
		Thread.sleep(6000);
		valueRet = StoragePorxy.get(key);
		assertArrayEquals(valueNull, valueRet);
	}
}
