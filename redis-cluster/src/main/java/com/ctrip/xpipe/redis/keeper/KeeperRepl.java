package com.ctrip.xpipe.redis.keeper;

/**
 * @author wenchao.meng
 * <p>
 * May 23, 2016
 */
public interface KeeperRepl {

	String replId();

	String replId2();

	Long secondReplIdOffset();

	long getBeginOffset();

	long getEndOffset();
}
