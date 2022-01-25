package org.coral.redis.cluster.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.manager.RcpSynManager;
import org.coral.redis.server.RedisMessageFactory;
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

public class RcpSynAliveHandler {
	private final int MAX_NODE = 1024;
	private AtomicBoolean runAlive = new AtomicBoolean(false);
	private final Logger LOGGER = LoggerFactory.getLogger(RcpSynAliveHandler.class);

	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

	private LinkedHashMap<String, ChannelHandlerContext> aliveMap = new LinkedHashMap<String, ChannelHandlerContext>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, ChannelHandlerContext> eldest) {
			return size() > MAX_NODE;
		}
	};

	public void addSyn(String key, ChannelHandlerContext ctx) {
		if (runAlive.compareAndSet(false, true)) {
			run();
		}
		aliveMap.put(key, ctx);
	}

	public void remove(String key) {
		aliveMap.remove(key);
	}

	public void run() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			for (Map.Entry<String, ChannelHandlerContext> entry : aliveMap.entrySet()) {
				String key = entry.getKey();
				try {
					ChannelHandlerContext channelHandlerContext = entry.getValue();
					if (!channelHandlerContext.channel().isWritable()) {
						RcpSynManager.stopSyn(key);
					}
					RedisMessage redisMessage = RedisMessageFactory.buildPING();
					channelHandlerContext.writeAndFlush(redisMessage);
				} catch (Exception e) {
					LOGGER.error("ping exception:{}", key, e);
					RcpSynManager.stopSyn(key);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.info("ping : {}", key);
				}


			}
		}, 10, 10, TimeUnit.SECONDS);
	}

	private static RcpSynAliveHandler aliveHandler = new RcpSynAliveHandler();

	public static RcpSynAliveHandler getInstance() {
		return aliveHandler;
	}
}
