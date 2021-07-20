package com.ctrip.xpipe.api.command;

/**
 * @author wenchao.meng
 * <p>
 * Jul 1, 2016
 */
public interface RequestResponseCommand<V> extends Command<V> {


	/**
	 * timeout, if <= 0, wait forever
	 *
	 * @return
	 */
	int getCommandTimeoutMilli();


}
