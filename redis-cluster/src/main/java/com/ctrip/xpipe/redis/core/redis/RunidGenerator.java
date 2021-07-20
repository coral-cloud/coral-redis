package com.ctrip.xpipe.redis.core.redis;

/**
 * @author wenchao.meng
 * <p>
 * Aug 19, 2016
 */
public interface RunidGenerator {

	RunidGenerator DEFAULT = new DefaultRunIdGenerator();

	String generateRunid();

}