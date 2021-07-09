package org.coral.redis.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhao
 * @createTime 2021-06-28 17:49:00
 */
@RestController
public class TendisController {
	boolean sign = false;
//
//	@GetMapping("/tendis/start")
//	public void sendTest(@RequestParam int count, @RequestParam int tcount) {
//		if (sign) {
//			return;
//		}
//		sign = true;
//		TestRedisExec redisExec = new TestRedisExec();
//		redisExec.testRedis(tcount, count, "tendis:", false);
//
//
//	}
//
//	@GetMapping("/tendis/stop")
//	public void stop() {
//		sign = false;
//	}
}
