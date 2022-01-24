package org.coral.redis.task;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.server.handler.PSynHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wuhao
 * @description: manager
 * @createTime 2021/12/11 13:01:00
 */

public class AliveProcessTask {
	private static final int MAX_NODE = 1024;
	private static AtomicBoolean runAlive = new AtomicBoolean(false);
	private static final Logger LOGGER = LoggerFactory.getLogger(AliveProcessTask.class);

	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

	private static LinkedHashMap<String, ChannelHandlerContext> aliveMap = new LinkedHashMap<String, ChannelHandlerContext>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, ChannelHandlerContext> eldest) {
			return size() > MAX_NODE;
		}
	};

	public static void addTask(String key, ChannelHandlerContext ctx) {
		if (runAlive.compareAndSet(false, true)) {
			run();
		}
		aliveMap.put(key, ctx);
	}

	public static void run() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			for (Map.Entry<String, ChannelHandlerContext> entry : aliveMap.entrySet()) {
				String key = entry.getKey();
				try {
					ChannelHandlerContext channelHandlerContext = entry.getValue();
					if (!channelHandlerContext.channel().isWritable()){
						aliveMap.remove(key);
						PSynHandler.stopSyn(key);
					}
					RedisMessage redisMessage = RedisMessageFactory.buildPING();
					channelHandlerContext.writeAndFlush(redisMessage);
				} catch (Exception e) {
					LOGGER.error("ping exception:{}", key, e);
					PSynHandler.stopSyn(key);
					aliveMap.remove(key);
				}
				if (LOGGER.isDebugEnabled()){
					LOGGER.info("ping : {}", key);
				}


			}
		}, 10, 10, TimeUnit.SECONDS);
	}


}
