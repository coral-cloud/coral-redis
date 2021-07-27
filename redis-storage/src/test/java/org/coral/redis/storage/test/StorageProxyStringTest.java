package org.coral.redis.storage.test;

import org.coral.redis.storage.RcpProxyString;
import org.coral.redis.storage.expire.RcpStorageExpireTask;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StorageProxyStringTest {
	public static void main(String[] args) throws InterruptedException {
		StorageProxyStringTest storageProxyTest = new StorageProxyStringTest();
		storageProxyTest.testExpire();
	}

	@Test
	public void testSetGet() throws InterruptedException {
		Thread thread = new Thread(new RcpStorageExpireTask());
		thread.start();
		byte[] key = "testKey".getBytes(StandardCharsets.UTF_8);
		byte[] value = "testValue".getBytes(StandardCharsets.UTF_8);
		byte[] valueNull = null;
		RcpProxyString.set(key, value, 0);
		byte[] valueRet = RcpProxyString.get(key);
		if (valueRet != null) {
			System.out.println(new String(valueRet));
		}
		assertArrayEquals(value, valueRet);
	}

	@Test
	public void testExpire() throws InterruptedException {
		Thread thread = new Thread(new RcpStorageExpireTask());
		thread.start();
		byte[] key = "testKey".getBytes(StandardCharsets.UTF_8);
		byte[] value = "testValue".getBytes(StandardCharsets.UTF_8);
		byte[] valueNull = null;
		RcpProxyString.set(key, value, 2);
		byte[] valueRet = RcpProxyString.get(key);
		if (valueRet != null) {
			System.out.println(new String(valueRet));
		}
		assertArrayEquals(value, valueRet);
		Thread.sleep(3000);
		valueRet = RcpProxyString.get(key);
		assertArrayEquals(valueNull, valueRet);
	}
}
