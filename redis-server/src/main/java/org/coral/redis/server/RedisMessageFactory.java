package org.coral.redis.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;

/**
 * @author wuhao
 * @createTime 2021-06-25 14:05:00
 */
public class RedisMessageFactory {
	public static RedisMessage buildPONG() {
		RedisMessage redisMessage = new SimpleStringRedisMessage("PONG");
		return redisMessage;
	}

	public static RedisMessage buildPING() {
		RedisMessage redisMessage = new SimpleStringRedisMessage("PING");
		return redisMessage;
	}

	public static RedisMessage buildOK() {
		RedisMessage redisMessage = new SimpleStringRedisMessage("OK");
		return redisMessage;
	}


	public static RedisMessage buildData(byte[] data) {
		if (data == null){
			return FullBulkStringRedisMessage.NULL_INSTANCE;
		}
		ByteBuf byteBuf = Unpooled.buffer(data.length);
		byteBuf.writeBytes(data);
		RedisMessage redisMessage = new FullBulkStringRedisMessage(byteBuf);
		return redisMessage;
	}

	public static RedisMessage buildError() {
		RedisMessage redisMessage = new SimpleStringRedisMessage("Error");
		return redisMessage;
	}
}
