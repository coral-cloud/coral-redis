package com.ctrip.xpipe.exception;

/**
 * @author wenchao.meng
 * <p>
 * Aug 11, 2016
 */
public interface ErrorMessageAware {

	ErrorMessage<?> getErrorMessage();

}
