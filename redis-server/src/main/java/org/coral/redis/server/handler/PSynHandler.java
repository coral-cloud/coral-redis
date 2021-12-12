package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.rdb.RdbDataStorage;
import org.coral.redis.manager.NodeManager;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.entity.data.RcpStringData;
import org.coral.redis.storage.entity.data.RcpType;
import org.coral.redis.storage.expire.RcpStorageExpireTask;
import org.coral.redis.storage.expire.RcpStorageSynTask;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.uils.IdUtils;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.util.StringUtils;
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
		;
		if (!(msgReq instanceof ArrayRedisMessage)){
			return Arrays.asList(RedisMessageFactory.buildError());
		}
		ArrayRedisMessage req = (ArrayRedisMessage) msgReq;
		String uid  = "";
		if (req.children().size() > 2){
			uid = RedisMsgUtils.getString(req.children().get(1));;
		}
		byte[] index = NodeManager.getIndex(uid);

		synData(ctx, uid);

		return (index != null) ? synAddData() : synFullData(uid);
	}

	private List<RedisMessage> synFullData(String uid) throws IOException {
		RedisMessage redisSync = RedisMessageFactory.buildSimple(getFullResp(uid));
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataStorage.getData());
		return Arrays.asList(redisSync, redisMessageData);

	}
	private List<RedisMessage> synAddData(){
		RedisMessage redisSync = RedisMessageFactory.buildSimple(getAddResp());
		return Arrays.asList(redisSync);
	}

	public void synData(ChannelHandlerContext ctx,  String uid) {
		byte[] index = NodeManager.getIndex(uid);
		RcpStorageSynTask.start(index, (key, value) -> {

			if (value.getRcpType() == RcpType.STRING) {

				RedisMessage redisMessageCmd = RedisMessageFactory.buildData("set".getBytes());
				RedisMessage redisMessageKey = RedisMessageFactory.buildData(key.getKey());
				RcpStringData rcpStringData = ObjectUtils.toObject(value.getContent(), RcpStringData.class);
				RedisMessage redisMessageValue = RedisMessageFactory.buildData(rcpStringData.getContent());
				ArrayRedisMessage redisMessage = new ArrayRedisMessage(Arrays.asList(redisMessageCmd, redisMessageKey, redisMessageValue));
				ctx.writeAndFlush(redisMessage);
				NodeManager.setIndex(uid, key.getKey());
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("synData: key: {}, value: {}", new String(key.getKey()), new String(rcpStringData.getContent()));
				}
			}


		});
	}

	/**
	 * FULLRESYNC db72e13d181ffb0d9cc7384c175a2371a530248b 1
	 *
	 * @param uid
	 * @return
	 */
	private String getFullResp(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("FULLRESYNC ").append(uid).append(" 1");
		return sb.toString();
	}

	private String getAddResp(){
		return "CONTINUE";
	}



}
