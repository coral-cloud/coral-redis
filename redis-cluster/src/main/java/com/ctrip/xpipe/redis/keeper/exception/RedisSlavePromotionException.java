package com.ctrip.xpipe.redis.keeper.exception;

/**
 * @author wenchao.meng
 * <p>
 * Jun 22, 2016
 */
public class RedisSlavePromotionException extends RedisKeeperException {

	private static final long serialVersionUID = 1L;

	public RedisSlavePromotionException(String message) {
		super(message);
	}

}
