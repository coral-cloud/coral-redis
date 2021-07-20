package com.ctrip.xpipe.monitor;

import com.ctrip.xpipe.api.monitor.Task;
import com.ctrip.xpipe.api.monitor.TransactionMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author wenchao.meng
 * <p>
 * Jan 3, 2017
 */
public class CatTransactionMonitor implements TransactionMonitor {

	public static Logger logger = LoggerFactory.getLogger(CatTransactionMonitor.class);

	@Override
	public void logTransactionSwallowException(String type, String name, Task task) {

	}

	@Override
	public void logTransaction(String type, String name, Task task) throws Exception {


	}

	@Override
	public <V> V logTransaction(String type, String name, Callable<V> task) throws Exception {
		return null;
	}

	@Override
	public <V> V logTransactionSwallowException(String type, String name, Callable<V> task) {


		return null;
	}
}
