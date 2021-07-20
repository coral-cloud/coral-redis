package com.ctrip.xpipe.api.lifecycle;

/**
 * @author wenchao.meng
 * <p>
 * 2016年4月21日 下午4:57:05
 */
public interface Stoppable {

	String PHASE_NAME_BEGIN = "stopping";

	String PHASE_NAME_END = "stopped";

	void stop() throws Exception;
}
