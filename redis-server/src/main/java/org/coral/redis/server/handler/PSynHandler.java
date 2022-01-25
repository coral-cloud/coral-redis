package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.manager.RcpSynManager;
import org.coral.redis.cluster.rdb.RdbDataFormat;
import org.coral.redis.manager.NodeContextManager;
import org.coral.redis.server.RedisMessageFactory;
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
		RcpSynManager.startSyn(uid, ctx);
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
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataFormat.getData());
		return Arrays.asList(redisSync, redisMessageData);

	}

	/**
	 * @return
	 */
	private List<RedisMessage> synAddData() {
		RedisMessage redisSync = RedisMessageFactory.buildSimple("CONTINUE");
		return Arrays.asList(redisSync);
	}



}
