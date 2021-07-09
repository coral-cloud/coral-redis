package org.coral.redis.storage.entity;

/**
 * @author wuhao
 * @description: RData
 * @createTime 2021/07/07 23:27:00
 */

public interface StorageData {
	/**
	 * 创建更新时间
	 * @return
	 */
	long getTime();

	/**
	 * 当前版本号
	 * @return
	 */
	default int getVersion() {
		return 0;
	};

	/**
	 * 是否过期
	 *
	 * @return
	 */
	default boolean isExpire(){
		if (getTime() == 0){
			return false;
		}
		if (getTime() < System.currentTimeMillis()){
			return true;
		}
		return false;
	}

	/**
	 * 返回数据实际内容
	 *
	 * @return
	 */
	byte[] getContent();

	/**
	 * 返回整体字节
	 *
	 * @return
	 */
	byte[] getBytes();

	StorageType getRType();

}
