package com.ctrip.xpipe.retry;

import com.ctrip.xpipe.api.retry.RetryPolicy;

/**
 * @author shyin
 * <p>
 * Sep 21, 2016
 */
public interface RetryPolicyFactory {
	RetryPolicy create();
}