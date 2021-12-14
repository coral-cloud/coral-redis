package org.coral.redis.manager;

import io.netty.handler.codec.redis.RedisMessage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author wuhao
 * @description: SlaveProcessManager
 * @createTime 2021/12/14 23:46:00
 */

public class SlaveProcessManager {
	private BlockingQueue<RedisMessage> blockingQueue = new ArrayBlockingQueue<RedisMessage>(100);

	public void start(){

	}
}
