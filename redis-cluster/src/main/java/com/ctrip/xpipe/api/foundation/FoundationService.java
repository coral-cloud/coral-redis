package com.ctrip.xpipe.api.foundation;

import com.ctrip.xpipe.api.lifecycle.Ordered;
import com.ctrip.xpipe.utils.ServicesUtil;

/**
 * basic inforamtion, like dc...
 *
 * @author wenchao.meng
 * <p>
 * Jun 13, 2016
 */
public interface FoundationService extends Ordered {

	FoundationService DEFAULT = ServicesUtil.getFoundationService();

	/**
	 * get current dc
	 *
	 * @return
	 */
	String getDataCenter();

	String getAppId();

	String getLocalIp();

}
