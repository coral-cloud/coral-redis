package com.ctrip.xpipe.redis.core.protocal;

import com.ctrip.xpipe.redis.core.protocal.protocal.EofType;

import java.io.IOException;

/**
 * @author wenchao.meng
 * <p>
 * 2016年3月29日 下午3:51:09
 */
public interface PsyncObserver {

	/**
	 * get FULLSYNC response
	 */
	void onFullSync();

	void reFullSync();

	/**
	 * get rdb length
	 *
	 * @param fileSize
	 * @param offset
	 * @throws IOException
	 */
	void beginWriteRdb(EofType eofType, long masterRdbOffset) throws IOException;


	void endWriteRdb();

	void onContinue(String requestReplId, String responseReplId);
}