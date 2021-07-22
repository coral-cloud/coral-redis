package org.coral.redis.storage.test;

import org.coral.redis.storage.entity.data.RcpZSetMtsKey;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-07-15 11:59:00
 */
public class RcpZSetMtsKeyTest {
	@Test
	public void testToKey() {
		for (int i = 0; i < 1; i++) {
			String key = "key" + i;
			String member = "member";
			int version = 1;
			System.out.println("------");
			RcpZSetMtsKey rcpZSetMtsKey = RcpZSetMtsKey.build(key.getBytes(StandardCharsets.UTF_8),
					version, member.getBytes(StandardCharsets.UTF_8));
			rcpZSetMtsKey.setMember(null);
			//System.out.println(Arrays.toString(rcpZSetMtsKey.getKey()));
			//System.out.println(Arrays.toString(rcpZSetMtsKey.getBytes()));
			System.out.println("------");
		}
	}
}
