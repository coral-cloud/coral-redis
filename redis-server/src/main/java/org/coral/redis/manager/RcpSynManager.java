package org.coral.redis.manager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.handler.RcpSynAliveHandler;
import org.coral.redis.cluster.handler.RcpSynRealTimeHandler;
import org.coral.redis.cluster.handler.RcpSynStorageHandler;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.entity.data.*;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * RcpSynManager
 *
 * @author wuhao
 * @createTime 2021/10/29 22:16:00
 */


public class RcpSynManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RcpSynManager.class);

	/**
	 * stopSyn
	 *
	 * @param uid
	 */
	public static void stopSyn(String uid) {
		NodeContext nodeContext = NodeContextManager.getContext(uid);
		if (nodeContext == null || nodeContext.getRcpSynStorageHandler() == null) {
			nodeContext.getRcpSynStorageHandler().stopTask();
		}
		NodeContextManager.removeContext(uid);
		RcpSynAliveHandler.getInstance().remove(uid);
		RcpSynRealTimeHandler.getInstance().remove(uid);
	}

	/**
	 * startSyn
	 * @param uid
	 * @param ctx
	 */
	public static void startSyn(String uid, ChannelHandlerContext ctx) {
		RcpSynAliveHandler.getInstance().addSyn(uid, ctx);
		RcpSynRealTimeHandler.getInstance().addSyn(uid, ctx);

		NodeContext nodeContext = NodeContextManager.getContext(uid);
		if (nodeContext == null){
			nodeContext = new NodeContext();
			nodeContext.setRcpSynStorageHandler(new RcpSynStorageHandler(nodeContext.getIndex(), (key, value) -> {

				if (value.getRcpType() == RcpType.STRING) {
					try {
						if (!ctx.channel().isWritable()) {
							stopSyn(uid);
						}
						RedisMessage redisMessageCmd = RedisMessageFactory.buildData("set".getBytes());
						RedisMessage redisMessageKey = RedisMessageFactory.buildData(key.getKey());
						RcpStringData rcpStringData = ObjectUtils.toObject(value.getContent(), RcpStringData.class);
						RedisMessage redisMessageValue = RedisMessageFactory.buildData(rcpStringData.getContent());
						ArrayRedisMessage redisMessage = new ArrayRedisMessage(Arrays.asList(redisMessageCmd, redisMessageKey, redisMessageValue));
						ctx.writeAndFlush(redisMessage);
						if (LOGGER.isInfoEnabled()) {
							LOGGER.info("synData: uid:{} key: {}, value: {}", uid, new String(key.getKey()), new String(rcpStringData.getContent()));
						}
						NodeContextManager.updateIndex(uid, key.getKey());
					} catch (Exception e) {
						LOGGER.error("synData Exception:{}:{}", uid, ctx.channel().remoteAddress(), e);
						RcpSynManager.stopSyn(uid);
					}
				}
			}));
		}
		NodeContextManager.putContext(uid, nodeContext);
	}

}
