package org.coral.redis.test;

import org.coral.redis.server.StoragePorxy;
import org.coral.redis.storage.expire.StorageExpireTask;

public class StorageProxyTest {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new StorageExpireTask());
		thread.start();
		String key = "testKey";
		String value = "testValue";
		StoragePorxy.set(key.getBytes(), value.getBytes(), 10);
		System.out.println("get key " +new String(StoragePorxy.get(key.getBytes())));

		while(true){
			Thread.sleep(1000);
			System.out.println("get key" +new String(StoragePorxy.get(key.getBytes())));
		}
	}
}
