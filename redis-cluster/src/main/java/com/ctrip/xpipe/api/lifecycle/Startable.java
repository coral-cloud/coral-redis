package com.ctrip.xpipe.api.lifecycle;

/**
 * @author wenchao.meng
 * <p>
 * 2016年4月21日 下午4:57:05
 */
public interface Startable {

	String PHASE_NAME_BEGIN = "starting";

	String PHASE_NAME_END = "started";

	void start() throws Exception;
}
