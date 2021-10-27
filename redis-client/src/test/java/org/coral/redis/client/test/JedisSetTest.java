package org.coral.redis.client.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhao
 * @createTime 2021-09-08 15:54:00
 */
public class JedisSetTest {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("10.40.0.176", 16380);
		Map<String, String> test = new HashMap<>();
		test.put("222", "222");
	}
}

/**

 jedis.zadd("10.40.0.176:16380", 1631062744733L, "test");
 *4
 $4
 ZADD
 $17
 10.40.0.176:16380
 $17
 1.631062744733E12
 $4
 test
 :1

 jedis.zremrangeByScore("10.40.0.176:16380", "1631062744733" , "1631062791224");
 *4
 $16
 ZREMRANGEBYSCORE
 $17
 10.40.0.176:16380
 $13
 1631062744733
 $13
 1631062791224
 :1

 jedis.zremrangeByScore("10.40.0.176:16380", 1631062744733L , 1631062791224L);
 *4
 $16
 ZREMRANGEBYSCORE
 $17
 10.40.0.176:16380
 $17
 1.631062744733E12
 $17
 1.631062791224E12
 :0

 jedis.zremrangeByScore("10.40.0.176:16380", 1631062791224L , 1631062791224L);
 *4
 $16
 ZREMRANGEBYSCORE
 $17
 10.40.0.176:16380
 $17
 1.631062791224E12
 $17
 1.631062791224E12
 :0


 jedis.zremrangeByScore("10.40.0.176:16380", "1631062744733" , "1631062791224");
 *4
 $16
 ZREMRANGEBYSCORE
 $17
 10.40.0.176:16380
 $13
 1631062744733
 $13
 1631062791224
 :0



 */
