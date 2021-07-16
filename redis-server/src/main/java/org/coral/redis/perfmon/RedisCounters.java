/*
 * FAE, Feinno App Engine
 *
 * Create by gaolei Nov 9, 2011
 *
 * Copyright (c) 2011 北京新媒传信科技有限公司
 */
package org.coral.redis.perfmon;

import org.helium.perfmon.PerformanceCounterFactory;
import org.helium.perfmon.PerformanceCounterType;
import org.helium.perfmon.SmartCounter;
import org.helium.perfmon.annotation.PerformanceCounter;
import org.helium.perfmon.annotation.PerformanceCounterCategory;

/**
 * RedisCounters
 *
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
@PerformanceCounterCategory(RedisCounters.INSTANCE_NAME)
public class RedisCounters {
	public static final String INSTANCE = "redis:";
	public static final String INSTANCE_NAME = INSTANCE + "monitor";

	@PerformanceCounter(name = "tx", type = PerformanceCounterType.TRANSACTION)
	private SmartCounter tx;

	public SmartCounter getTx() {
		return tx;
	}

	public static RedisCounters getInstance(String name) {
		RedisCounters perfmonCounters = PerformanceCounterFactory.getCounters(RedisCounters.class, INSTANCE + name);
		return perfmonCounters;
	}

}

