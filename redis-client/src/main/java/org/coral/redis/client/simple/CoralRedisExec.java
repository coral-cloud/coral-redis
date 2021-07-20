package org.coral.redis.client.simple;

import org.helium.perfmon.MonitorServer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author wuhao
 * @createTime 2021-07-05 14:43:00
 */
public class CoralRedisExec {
	private String ip = "127.0.0.1";
	private int port = 6399;
	private int ttl = 7 * 24 * 60 * 60;
	private boolean sign = true;

	/**
	 * @param threadNum
	 * @param simpleThreadCount
	 * @param size
	 * @throws InterruptedException
	 */
	public void setGetTest(int threadNum, long simpleThreadCount, int size) throws InterruptedException {

		for (int i = 0; i < threadNum; i++) {
			String key = UUID.randomUUID().toString();
			System.out.println("setGetTest uuid:" + key + "{num}");
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					CoralRedisClient greapClient = new CoralRedisClient(getIp(), getPort());
					for (long j = 0; j < simpleThreadCount; j++) {
						byte[] setKey = (key + j).getBytes(StandardCharsets.UTF_8);
						byte[] content = new byte[size];
						for (int ss = 0; ss < setKey.length && ss < content.length; ss++) {
							content[ss] = setKey[ss];
						}
						greapClient.set(setKey, content, getTtl());
						//greapClient.get(setKey);
					}
				}
			});
			thread.start();

		}

	}

	public String getIp() {
		return getSetting("CR_RDIP", "127.0.0.1");
	}

	public int getPort() {
		return Integer.parseInt(getSetting("CR_RDPORT", "6399"));
	}

	public int getTtl() {
		if (sign){
			ttl = Integer.parseInt(getSetting("CR_TTL", "0"));
			sign = false;
		}

		return ttl;
	}

	/**
	 * CR_RDIP 10.3.4.111
	 * CR_RDPORT 9221
	 * CR_THREAD 2
	 * CR_TESTNUM 1000000
	 * CR_TESTSIZE 128
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		CoralRedisExec coralRedisExec = new CoralRedisExec();
		MonitorServer.run(8081);
		int threadNum = Integer.parseInt(getSetting("CR_THREAD", "2"));
		long testNum = Long.parseLong(getSetting("CR_TESTNUM", "1000000"));
		int testSize = Integer.parseInt(getSetting("CR_TESTSIZE", "128"));
		coralRedisExec.setGetTest(threadNum, testNum, testSize);
	}


	public static String getSetting(String key, String defaultValue) {
		String value = defaultValue;
		if (System.getenv(key) != null) {
			value = System.getenv(key);
		}
		System.out.println("key:" + key + "  value:" + value);
		return value;
	}
}
