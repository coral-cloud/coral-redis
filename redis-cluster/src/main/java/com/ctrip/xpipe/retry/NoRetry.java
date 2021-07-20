package com.ctrip.xpipe.retry;


/**
 * @author wenchao.meng
 * <p>
 * Jul 9, 2016
 */
public class NoRetry<V> extends RetryNTimes<V> {
	public NoRetry() {
		super(0);
	}

}
