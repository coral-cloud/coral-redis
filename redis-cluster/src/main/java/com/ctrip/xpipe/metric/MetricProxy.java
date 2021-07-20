package com.ctrip.xpipe.metric;

import com.ctrip.xpipe.api.lifecycle.Ordered;

/**
 * @author marsqing
 * <p>
 * Dec 7, 2016 12:04:00 AM
 */
public interface MetricProxy extends Ordered {

	void writeBinMultiDataPoint(MetricData data) throws MetricProxyException;

}
