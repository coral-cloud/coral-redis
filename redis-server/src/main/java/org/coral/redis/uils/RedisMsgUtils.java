package org.coral.redis.uils;

import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.util.CharsetUtil;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:34:00
 */
public class RedisMsgUtils {
	/**
	 * @param msg
	 * @return
	 */
	public static String getString(FullBulkStringRedisMessage msg) {
		if (msg.isNull()) {
			return "(null)";
		}
		return msg.content().toString(CharsetUtil.UTF_8);
	}
}
