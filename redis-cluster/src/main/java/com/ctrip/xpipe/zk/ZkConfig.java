package com.ctrip.xpipe.zk;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author wenchao.meng
 * <p>
 * Jun 23, 2016
 */
public interface ZkConfig {

	String DEFAULT_ZK_NAMESPACE = "xpipe";

	int getZkConnectionTimeoutMillis();

	int getZkCloseWaitMillis();

	String getZkNamespace();

	int getZkRetries();

	int getSleepMsBetweenRetries();

	int getZkSessionTimeoutMillis();

	int waitForZkConnectedMillis();

	CuratorFramework create(String address) throws InterruptedException;

}
