package org.coral.redis.client.test;

import org.coral.redis.client.GreapClient;

/**
 * @author wuhao
 * @createTime 2021-06-15 09:48:00
 */
public class GreapClientTest {
	public static void main(String[] args) throws InterruptedException {
		GreapClientTest greapClientTest = new GreapClientTest();
		greapClientTest.setGetTest();
	}

	/**
	 * *4
	 * $5
	 * SETEX
	 * $3
	 * 111
	 * $6
	 * 100000
	 * $3
	 * 111
	 * +OK
	 * *2
	 * $3
	 * GET
	 * $3
	 * 111
	 * $3
	 * 111
	 */
	public void setGetTest() throws InterruptedException {
		String key = "111";
		for (int i = 0 ; i < 10; i++){
			Thread thread =  new Thread(new Runnable() {
				@Override
				public void run() {
					GreapClient greapClient = new GreapClient(6399);
					for (int i = 0; i < 100000000; i++){
						greapClient.set(key, key, 100000);
						greapClient.get(key);
					}
				}
			});
			thread.start();

		}

		Thread.sleep(10000);
		System.out.println("OK");
	}
}
