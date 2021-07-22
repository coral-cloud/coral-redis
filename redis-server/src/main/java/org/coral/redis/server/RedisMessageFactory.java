package org.coral.redis.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.redis.*;
import org.coral.redis.storage.entity.data.RcpZSetRow;
import org.coral.redis.storage.utils.ByteUtils;

import java.util.ArrayList;
import java.util.List;

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

	public static RedisMessage buildNum(int num) {
		RedisMessage redisMessage = new IntegerRedisMessage(num);
		return redisMessage;
	}


	public static RedisMessage buildData(byte[] data) {
		if (data == null) {
			return FullBulkStringRedisMessage.NULL_INSTANCE;
		}
		ByteBuf byteBuf = Unpooled.buffer(data.length);
		byteBuf.writeBytes(data);
		RedisMessage redisMessage = new FullBulkStringRedisMessage(byteBuf);
		return redisMessage;
	}

	public static ArrayRedisMessage buildZSetArrayData(List<RcpZSetRow> rcpZSetRows, boolean withScores) {
		if (rcpZSetRows == null) {
			return ArrayRedisMessage.EMPTY_INSTANCE;
		}
		List<RedisMessage> redisMessageList = new ArrayList<>();
		for (RcpZSetRow rcpZSetRow : rcpZSetRows) {
			if (rcpZSetRow.getRcpZSetStmKey() != null){
				ByteBuf byteBuf = Unpooled.buffer(rcpZSetRow.getRcpZSetStmKey().getMember().length);
				byteBuf.writeBytes(rcpZSetRow.getRcpZSetStmKey().getMember());
				RedisMessage redisMessage = new FullBulkStringRedisMessage(byteBuf);
				redisMessageList.add(redisMessage);
				if (withScores){
					//TODO 这里好像有点问题，按理说应该是返回double，但是看抓包为整形，先强转一下吧
					int scores = (int) rcpZSetRow.getRcpZSetStmKey().getScore();
					byte[] scoresBytes = ByteUtils.stringToBytes(String.valueOf(scores));
					ByteBuf byteBufScores = Unpooled.buffer(scoresBytes.length);
					byteBufScores.writeBytes(scoresBytes);
					RedisMessage redisMessageScores = new FullBulkStringRedisMessage(byteBufScores);
					redisMessageList.add(redisMessageScores);
				}

			}

		}
		ArrayRedisMessage arrayRedisMessage = new ArrayRedisMessage(redisMessageList);
		return arrayRedisMessage;
	}

	public static RedisMessage buildError() {
		RedisMessage redisMessage = new SimpleStringRedisMessage("Error");
		return redisMessage;
	}
}
