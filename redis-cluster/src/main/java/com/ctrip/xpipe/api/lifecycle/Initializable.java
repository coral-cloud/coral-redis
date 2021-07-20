package com.ctrip.xpipe.api.lifecycle;

/**
 * @author wenchao.meng
 * <p>
 * 2016年4月21日 下午4:57:05
 */
public interface Initializable {

	String PHASE_NAME_BEGIN = "initializing";

	String PHASE_NAME_END = "initialized";

	void initialize() throws Exception;
}
