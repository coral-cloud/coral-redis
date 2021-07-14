package org.coral.redis.storage;

import org.coral.redis.storage.entity.*;
import org.coral.redis.storage.entity.type.RcpType;
import org.coral.redis.storage.impl.StorageDbFactory;

import java.util.List;
import java.util.Map;

public class StorageProxyZSet {

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
			StorageClientZSet.getInstance().zadd(rcpZSetRow);
		}
		return true;
	}

	/**
	 * @param key
	 * @param start
	 * @param stop
	 */
	public static List<RcpZSetRow> zrange(byte[] key, long start, long stop) {
		RcpMetaKey rcpMetaKey = RcpMetaKey.build(key);
		StorageClientZSet.getInstance().zrange(rcpMetaKey, start, stop);
		return null;
	}
}
