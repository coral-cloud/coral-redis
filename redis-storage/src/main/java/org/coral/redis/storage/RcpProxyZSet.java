package org.coral.redis.storage;

import org.coral.redis.storage.entity.data.RcpMetaKey;
import org.coral.redis.storage.entity.data.RcpZSetRow;
import org.coral.redis.storage.storage.impl.RcpZSetDb;

import java.util.List;
import java.util.Map;

public class RcpProxyZSet {

	/**
	 * zadd
	 *
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public static boolean zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		List<RcpZSetRow> rcpZSetRows = RcpZSetRow.build(key, scoreMembers, 0, 0);
		for (RcpZSetRow rcpZSetRow : rcpZSetRows) {
			RcpZSetDb.getInstance().zadd(rcpZSetRow);
		}
		return true;
	}

	/**
	 * @param key
	 * @param start
	 * @param stop
	 */
	public static List<RcpZSetRow> zrange(byte[] key, int start, int stop) {
		RcpMetaKey rcpMetaKey = RcpMetaKey.build(key);
		List<RcpZSetRow> rcpZSetRows = RcpZSetDb.getInstance().zrange(rcpMetaKey, start, stop);
		return rcpZSetRows;
	}
}
