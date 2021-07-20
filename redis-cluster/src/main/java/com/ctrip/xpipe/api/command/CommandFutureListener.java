package com.ctrip.xpipe.api.command;

/**
 * @author wenchao.meng
 * <p>
 * Jul 1, 2016
 */
public interface CommandFutureListener<V> {

	void operationComplete(CommandFuture<V> commandFuture) throws Exception;
}
