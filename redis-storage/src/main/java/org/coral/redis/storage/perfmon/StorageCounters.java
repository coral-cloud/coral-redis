package org.coral.redis.storage.perfmon;

import org.helium.perfmon.PerformanceCounterFactory;
import org.helium.perfmon.PerformanceCounterType;
import org.helium.perfmon.SmartCounter;
import org.helium.perfmon.annotation.PerformanceCounter;
import org.helium.perfmon.annotation.PerformanceCounterCategory;

/**
 * StorageCounters
 *
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
@PerformanceCounterCategory(StorageCounters.INSTANCE_NAME)
public class StorageCounters {
	public static final String INSTANCE = "rocksdb:";
	public static final String INSTANCE_NAME = INSTANCE + "monitor";

	@PerformanceCounter(name = "tx", type = PerformanceCounterType.TRANSACTION)
	private SmartCounter tx;


	public SmartCounter getTx() {
		return tx;
	}

	public static StorageCounters getInstance(String name) {
		StorageCounters perfmonCounters = PerformanceCounterFactory.getCounters(StorageCounters.class,
				INSTANCE + name);
		return perfmonCounters;
	}

}

