package com.ctrip.xpipe.api.lifecycle;

/**
 * @author wenchao.meng
 * <p>
 * 2016年4月21日 下午4:57:05
 */
public interface Disposable {

	String PHASE_NAME_BEGIN = "disposing";

	String PHASE_NAME_END = "disposed";

	void dispose() throws Exception;
}
