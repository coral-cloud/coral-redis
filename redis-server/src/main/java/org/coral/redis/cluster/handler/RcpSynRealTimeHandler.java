package org.coral.redis.cluster.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.manager.RcpSynManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/**
 * @author wuhao
 * @description: RcpSynRealTimeHandler
 * @createTime 2021/12/14 23:46:00
 */

public class RcpSynRealTimeHandler {
	private BlockingQueue<RedisMessage> blockingQueue = new ArrayBlockingQueue<RedisMessage>(100);

	private final int MAX_NODE = 1024;
	private AtomicBoolean runAlive = new AtomicBoolean(false);

	private final Logger LOGGER = LoggerFactory.getLogger(RcpSynRealTimeHandler.class);

	private LinkedHashMap<String, ChannelHandlerContext> aliveMap = new LinkedHashMap<String, ChannelHandlerContext>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, ChannelHandlerContext> eldest) {
			return size() > MAX_NODE;
		}
	};


	private RcpSynRealTimeHandler() {
	}
	public void remove(String key){
		aliveMap.remove(key);
	}
	/**
	 * @param key
	 * @param ctx
	 */
	public void addSyn(String key, ChannelHandlerContext ctx) {
		if (runAlive.compareAndSet(false, true)) {
			runTask();
		}
		aliveMap.put(key, ctx);
	}

	/**
	 * addMsg
	 *
	 * @param redisMessage
	 */
	public void addMsg(RedisMessage redisMessage) {
		blockingQueue.add(redisMessage);

	}

	public void runTask() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						RedisMessage redisMessage = blockingQueue.take();
						if (redisMessage != null) {
							aliveMap.forEach(new BiConsumer<String, ChannelHandlerContext>() {
								@Override
								public void accept(String s, ChannelHandlerContext context) {
									try {
										context.writeAndFlush(redisMessage);
									} catch (Exception e) {
										RcpSynManager.stopSyn(s);
										LOGGER.error("syn uid:{} error.", s, e);
									}
								}
							});
						}
					} catch (Exception e) {
						LOGGER.error("", e);
					}
				}

			}
		});
		thread.start();
	}

	private static RcpSynRealTimeHandler realTimeHandler = new RcpSynRealTimeHandler();

	public static RcpSynRealTimeHandler getInstance() {
		return realTimeHandler;
	}

}
