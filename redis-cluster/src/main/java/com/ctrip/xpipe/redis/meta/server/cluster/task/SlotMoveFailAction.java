package com.ctrip.xpipe.redis.meta.server.cluster.task;

/**
 * @author wenchao.meng
 * <p>
 * Jul 26, 2016
 */
public interface SlotMoveFailAction {

	void onFail(SlotMoveTask slotMoveTask);

	void onSuccess(SlotMoveTask slotMoveTask);

}
