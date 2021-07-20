package com.ctrip.xpipe.monitor;

import com.ctrip.xpipe.api.monitor.EventMonitor;
import com.ctrip.xpipe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leoliang
 * <p>
 * 2017年3月1日
 */
public class CatEventMonitor implements EventMonitor {

	private final String FAIL = "fail";
	private final int typeMaxLen = 128;
	private static final Logger logger = LoggerFactory.getLogger(CatEventMonitor.class);


	private String shorten(String simpleAlertMessage) {
		return StringUtil.subHead(simpleAlertMessage, typeMaxLen);
	}

	@Override
	public void logEvent(String type, String name, long count) {

	}

	@Override
	public void logEvent(String type, String name) {

	}

	@Override
	public void logAlertEvent(String simpleAlertMessage) {

	}
}
