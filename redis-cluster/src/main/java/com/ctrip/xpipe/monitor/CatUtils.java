package com.ctrip.xpipe.monitor;

import com.ctrip.xpipe.api.command.CommandFuture;
import com.ctrip.xpipe.concurrent.DefaultExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author wenchao.meng
 * <p>
 * Aug 29, 2016
 */
public class CatUtils {

	private static Logger logger = LoggerFactory.getLogger(CatUtils.class);

	private static ExecutorService executors = DefaultExecutorFactory.createAllowCoreTimeoutAbortPolicy("CatUtils").createExecutorService();

	public static void newFutureTaskTransaction(final String type, final String name, final CommandFuture<?> future) {

		if (!CatConfig.isCatenabled()) {
			return;
		}


	}
}
