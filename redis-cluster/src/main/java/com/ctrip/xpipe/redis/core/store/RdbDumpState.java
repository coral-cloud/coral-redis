package com.ctrip.xpipe.redis.core.store;

/**
 * @author wenchao.meng
 * <p>
 * Aug 25, 2016
 */
public enum RdbDumpState {

	NORMAL,
	WAIT_DUMPPING,
	DUMPING,
	FAIL

}
