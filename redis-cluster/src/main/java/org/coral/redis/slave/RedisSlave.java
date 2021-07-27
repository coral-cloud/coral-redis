package org.coral.redis.slave;

import java.io.IOException;

/**
 * @author wuhao
 * @description: RedisSlave
 * @createTime 2021/07/21 23:06:00
 */

public class RedisSlave {
	public void start(String ip, int port) throws IOException {
		ReplicatorTask replicatorTask = new ReplicatorTask(ip, port);
		Thread thread = new Thread(replicatorTask);
		thread.start();

	}

}
