package com.ctrip.xpipe.api.cluster;

/**
 * @author wenchao.meng
 * <p>
 * Jul 21, 2016
 */
public interface LeaderAware {

	void isleader();

	void notLeader();

}
