package org.coral.redis.client.test;


import org.coral.redis.client.simple.CoralRedisExec;
import org.helium.perfmon.MonitorServer;

/**
 * @author wuhao
 * @createTime 2021-06-15 09:48:00
 */
public class CoralRedisClientTest {

	public static void main(String[] args) throws InterruptedException {
		CoralRedisExec greapClientTest = new CoralRedisExec();
		//MonitorServer.run(8081);
		greapClientTest.setGetTest(8, 10000000, 64);
	}

}
