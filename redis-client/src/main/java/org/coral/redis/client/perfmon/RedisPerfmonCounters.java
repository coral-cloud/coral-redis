/*
 * FAE, Feinno App Engine
 *
 * Create by gaolei Nov 9, 2011
 *
 * Copyright (c) 2011 北京新媒传信科技有限公司
 */
package org.coral.redis.client.perfmon;

import org.helium.perfmon.PerformanceCounterFactory;
import org.helium.perfmon.PerformanceCounterType;
import org.helium.perfmon.SmartCounter;
import org.helium.perfmon.annotation.PerformanceCounter;
import org.helium.perfmon.annotation.PerformanceCounterCategory;

/**
 *
 */
@PerformanceCounterCategory("redis")
public class RedisPerfmonCounters {
	@PerformanceCounter(name = "tx", type = PerformanceCounterType.TRANSACTION)
	private SmartCounter tx;


	public SmartCounter getTx() {
		return tx;
	}

	public static RedisPerfmonCounters getInstance(String operator) {
		RedisPerfmonCounters perfmonCounters = PerformanceCounterFactory.getCounters(RedisPerfmonCounters.class, operator);
		return perfmonCounters;
	}

}

