package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.handler.RcpSynStorageHandler;
import org.coral.redis.cluster.rdb.RdbDataStorage;
import org.coral.redis.manager.NodeContext;
import org.coral.redis.task.AliveProcessTask;
import org.coral.redis.manager.NodeContextManager;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.entity.data.RcpStringData;
import org.coral.redis.storage.entity.data.RcpType;
import org.coral.redis.task.RcpStorageSynTask;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.uils.RedisMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: PSynHandler
 * @createTime 2021/10/28 22:35:00
 */

public class PSynHandler implements CommandHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(PSynHandler.class);

	@Override
	public List<RedisMessage> process(ChannelHandlerContext ctx, String command, RedisMessage msgReq) throws Exception {

		if (!(msgReq instanceof ArrayRedisMessage)) {
			return Arrays.asList(RedisMessageFactory.buildError());
		}
		ArrayRedisMessage req = (ArrayRedisMessage) msgReq;
		String uid = "";
		if (req.children().size() > 2) {
			uid = RedisMsgUtils.getString(req.children().get(1));
		}
		uid = NodeContextManager.updateUid(uid);
		startSyn(uid, ctx);
		AliveProcessTask.addTask(uid, ctx);

		return (NodeContextManager.getContext(uid) != null) ? synAddData() : synFullData(uid);
	}

	/**
	 * FULLRESYNC db72e13d181ffb0d9cc7384c175a2371a530248b 1
	 *
	 * @param uid
	 * @return
	 */
	private List<RedisMessage> synFullData(String uid) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("FULLRESYNC ").append(uid).append(" 1");
		RedisMessage redisSync = RedisMessageFactory.buildSimple(sb.toString());
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataStorage.getData());
		return Arrays.asList(redisSync, redisMessageData);

	}

	/**
	 * @return
	 */
	private List<RedisMessage> synAddData() {
		RedisMessage redisSync = RedisMessageFactory.buildSimple("CONTINUE");
		return Arrays.asList(redisSync);
	}


	/**
	 * startSyn
	 * @param uid
	 * @param ctx
	 */
	public static void startSyn(String uid, ChannelHandlerContext ctx) {
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
						stopSyn(uid);
					}
				}
			}));
		}

	}

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
	}


}
