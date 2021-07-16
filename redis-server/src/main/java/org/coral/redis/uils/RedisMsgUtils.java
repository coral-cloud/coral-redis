package org.coral.redis.uils;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.CharsetUtil;
import org.coral.redis.server.CommandSign;
import org.coral.redis.storage.utils.ByteUtils;
import org.helium.util.StringUtils;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:34:00
 */
public class RedisMsgUtils {
	/**
	 * getString
	 *
	 * @param redisMessage
	 * @return String
	 */
	public static String getString(RedisMessage redisMessage) {
		if (redisMessage == null) {
			return null;
		}
		if (redisMessage instanceof FullBulkStringRedisMessage) {
			FullBulkStringRedisMessage msg = (FullBulkStringRedisMessage) redisMessage;
			if (msg.isNull()) {
				return "(null)";
			}
			return msg.content().toString(CharsetUtil.UTF_8);
		}
		throw new UnsupportedOperationException("not support message:" + redisMessage.getClass());

	}

	/**
	 * getBytes
	 *
	 * @param redisMessage
	 * @return byte[]
	 */
	public static byte[] getBytes(RedisMessage redisMessage) {
		String str = getString(redisMessage);
		if (StringUtils.isNullOrEmpty(str)) {
			return null;
		}
		return ByteUtils.stringToBytes(str);
	}
	/**
	 * getInteger
	 *
	 * @param redisMessage
	 * @return int
	 */
	public static int getInteger(RedisMessage redisMessage) {
		String str = getString(redisMessage);
		if (StringUtils.isNullOrEmpty(str)) {
			return 0;
		}
		return Integer.parseInt(str);
	}

	/**
	 * getExpire
	 *
	 * @param redisMessage
	 * @return int
	 */
	public static int getExpire(ArrayRedisMessage redisMessage) {
		if (redisMessage.children().size() < 4) {
			return -1;
		}
		for (int i = 3; i < redisMessage.children().size(); i++) {
			String msgStr = RedisMsgUtils.getString(redisMessage.children().get(i));
			if (msgStr.toUpperCase().equals(CommandSign.P_EX)) {
				int expire = RedisMsgUtils.getInteger(redisMessage.children().get(i + 1));
				return expire;
			}
		}
		return -1;
	}

}
