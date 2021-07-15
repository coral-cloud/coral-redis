package org.coral.redis.storage.test;

import org.coral.redis.storage.StorageProxyZSet;
import org.coral.redis.storage.entity.RcpZSetRow;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageProxyZSetTest {
	public static void main(String[] args) throws InterruptedException {
		StorageProxyZSetTest storageProxyTest = new StorageProxyZSetTest();
		storageProxyTest.testZAddZRange();
	}

	@Test
	public void testZAddZRange() throws InterruptedException {

		byte[] key = "testKey1".getBytes(StandardCharsets.UTF_8);
		Map<byte[], Double> data = new HashMap<>();
		data.put("234".getBytes(StandardCharsets.UTF_8), 7d);
		data.put("1324".getBytes(StandardCharsets.UTF_8), 6d);
		data.put("1234".getBytes(StandardCharsets.UTF_8), 9d);
		StorageProxyZSet.zadd(key, data);
		List<RcpZSetRow> rcpZSetRows = StorageProxyZSet.zrange(key, 0, 6);
		for (RcpZSetRow rcpZSetRow : rcpZSetRows) {
			System.out.println(rcpZSetRow.getRcpZSetStmKey().getScore() + ":"
					+ new String(rcpZSetRow.getRcpZSetStmKey().getMember()));
		}
	}

}
