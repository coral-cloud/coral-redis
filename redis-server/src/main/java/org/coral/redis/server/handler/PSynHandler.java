package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.rdb.RdbDataStorage;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.entity.data.RcpStringData;
import org.coral.redis.storage.entity.data.RcpType;
import org.coral.redis.storage.expire.RcpStorageExpireTask;
import org.coral.redis.storage.expire.RcpStorageSynTask;
import org.coral.redis.storage.protostuff.ObjectUtils;
import org.coral.redis.uils.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		RedisMessage redisSync = RedisMessageFactory.buildSimple(getRespCommand());
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataStorage.getData());

		synData(ctx);

		return Arrays.asList(redisSync, redisMessageData);
	}

	public void synData(ChannelHandlerContext ctx) {
		RcpStorageSynTask.start(0, (key, value) -> {

			if (value.getRcpType() == RcpType.STRING){

				RedisMessage redisMessageCmd = RedisMessageFactory.buildData("set".getBytes());
				RedisMessage redisMessageKey = RedisMessageFactory.buildData(key.getKey());
				RcpStringData rcpStringData = ObjectUtils.toObject(value.getContent(), RcpStringData.class);
				RedisMessage redisMessageValue = RedisMessageFactory.buildData(rcpStringData.getContent());
				ArrayRedisMessage redisMessage = new ArrayRedisMessage(Arrays.asList(redisMessageCmd, redisMessageKey, redisMessageValue));
				ctx.writeAndFlush(redisMessage);
				if (LOGGER.isInfoEnabled()){
					LOGGER.info("synData: key: {}, value: {}", new String(key.getKey()), new String(rcpStringData.getContent()));
				}
			}


		});
	}

	public String getRespCommand() {
		String command = "FULLRESYNC db72e13d181ffb0d9cc7384c175a2371a530248b 1";
		return command;
	}


}
