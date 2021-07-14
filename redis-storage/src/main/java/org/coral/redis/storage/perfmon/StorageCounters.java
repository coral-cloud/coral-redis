package org.coral.redis.storage.perfmon;

import org.helium.perfmon.PerformanceCounterFactory;
import org.helium.perfmon.PerformanceCounterType;
import org.helium.perfmon.SmartCounter;
import org.helium.perfmon.annotation.PerformanceCounter;
import org.helium.perfmon.annotation.PerformanceCounterCategory;

/**
 * <b>描述：</b>数据库性能计数器
 * <p>
 * <b>功能：</b>对数据库访问的频率、次数、进行计数。参考{@link PerformanceCounterFactory}
 * <p>
 * wuhao
 */
@PerformanceCounterCategory(StorageCounters.INSTANCE)
public class StorageCounters {
	public static final String INSTANCE = "stroage:";
	@PerformanceCounter(name = "tx", type = PerformanceCounterType.TRANSACTION)
	private SmartCounter tx;


	public SmartCounter getTx() {
		return tx;
	}

	public static StorageCounters getInstance(String name) {
		StorageCounters perfmonCounters = PerformanceCounterFactory.getCounters(StorageCounters.class, INSTANCE + name);
		return perfmonCounters;
	}

}

