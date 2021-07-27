package org.coral.redis.storage.storage;

/**
 * 参考：
 * http://tendis.cn/#/Tendisplus/%E8%BF%90%E7%BB%B4/conf_templ
 * https://github.com/OpenAtomFoundation/pika/wiki/pika-%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E8%AF%B4%E6%98%8E
 * https://www.jianshu.com/p/1bd03c9cf1ac
 * @author wuhao
 * @createTime 2021-07-20 16:34:00
 */
public class RocksDbConfig {
	private static class StorageConfigInit {
		private static RocksDbConfig storageConfig = new RocksDbConfig();
	}

	public static RocksDbConfig getInstance() {
		return StorageConfigInit.storageConfig;
	}

	private int cacheIndexAndFilterBlocks = 0;
	private int maxOpenFiles = -1;
	private int compressType = -1;
	private boolean level0CompressEnabled = false;
	private boolean level1CompressEnabled = false;
	private boolean levelCompactionDynamicLevelBytes = true;
	//# [n >= 4, n <= 64, n ~= cpu_cores][64 16 14]/[4 1 1]
	private int maxBackgroundCompactions = 4;
	private int maxWriteBufferNumber = 1;
	private int minWriteBufferNumberToMerge = 1;
	private long writeBufferSize = 67108864;
	private long targetFileSizeBase = 67108864;
	private long maxBytesForLevelBase = 536870912;
	private int numLevels = 4;
	private int maxBytesForLevelMultiplier = 8;
	private int level0FileNumCompactionTrigger = 8;
	private int level0SlowdownWritesTrigger = 17;
	private int level0StopWritesTrigger = 24;

	public int getCacheIndexAndFilterBlocks() {
		return cacheIndexAndFilterBlocks;
	}

	public void setCacheIndexAndFilterBlocks(int cacheIndexAndFilterBlocks) {
		this.cacheIndexAndFilterBlocks = cacheIndexAndFilterBlocks;
	}

	public int getMaxOpenFiles() {
		return maxOpenFiles;
	}

	public void setMaxOpenFiles(int maxOpenFiles) {
		this.maxOpenFiles = maxOpenFiles;
	}

	public int getCompressType() {
		return compressType;
	}

	public void setCompressType(int compressType) {
		this.compressType = compressType;
	}

	public boolean isLevel0CompressEnabled() {
		return level0CompressEnabled;
	}

	public void setLevel0CompressEnabled(boolean level0CompressEnabled) {
		this.level0CompressEnabled = level0CompressEnabled;
	}

	public boolean isLevel1CompressEnabled() {
		return level1CompressEnabled;
	}

	public void setLevel1CompressEnabled(boolean level1CompressEnabled) {
		this.level1CompressEnabled = level1CompressEnabled;
	}

	public boolean isLevelCompactionDynamicLevelBytes() {
		return levelCompactionDynamicLevelBytes;
	}

	public void setLevelCompactionDynamicLevelBytes(boolean levelCompactionDynamicLevelBytes) {
		this.levelCompactionDynamicLevelBytes = levelCompactionDynamicLevelBytes;
	}

	public int getMaxBackgroundCompactions() {
		return maxBackgroundCompactions;
	}

	public void setMaxBackgroundCompactions(int maxBackgroundCompactions) {
		this.maxBackgroundCompactions = maxBackgroundCompactions;
	}

	public int getMaxWriteBufferNumber() {
		return maxWriteBufferNumber;
	}

	public void setMaxWriteBufferNumber(int maxWriteBufferNumber) {
		this.maxWriteBufferNumber = maxWriteBufferNumber;
	}

	public int getMinWriteBufferNumberToMerge() {
		return minWriteBufferNumberToMerge;
	}

	public void setMinWriteBufferNumberToMerge(int minWriteBufferNumberToMerge) {
		this.minWriteBufferNumberToMerge = minWriteBufferNumberToMerge;
	}

	public long getWriteBufferSize() {
		return writeBufferSize;
	}

	public void setWriteBufferSize(long writeBufferSize) {
		this.writeBufferSize = writeBufferSize;
	}

	public long getMaxBytesForLevelBase() {
		return maxBytesForLevelBase;
	}

	public void setMaxBytesForLevelBase(long maxBytesForLevelBase) {
		this.maxBytesForLevelBase = maxBytesForLevelBase;
	}

	public long getTargetFileSizeBase() {
		return targetFileSizeBase;
	}

	public void setTargetFileSizeBase(long targetFileSizeBase) {
		this.targetFileSizeBase = targetFileSizeBase;
	}

	public int getNumLevels() {
		return numLevels;
	}

	public void setNumLevels(int numLevels) {
		this.numLevels = numLevels;
	}

	public int getMaxBytesForLevelMultiplier() {
		return maxBytesForLevelMultiplier;
	}

	public void setMaxBytesForLevelMultiplier(int maxBytesForLevelMultiplier) {
		this.maxBytesForLevelMultiplier = maxBytesForLevelMultiplier;
	}

	public int getLevel0FileNumCompactionTrigger() {
		return level0FileNumCompactionTrigger;
	}

	public void setLevel0FileNumCompactionTrigger(int level0FileNumCompactionTrigger) {
		this.level0FileNumCompactionTrigger = level0FileNumCompactionTrigger;
	}

	public int getLevel0SlowdownWritesTrigger() {
		return level0SlowdownWritesTrigger;
	}

	public void setLevel0SlowdownWritesTrigger(int level0SlowdownWritesTrigger) {
		this.level0SlowdownWritesTrigger = level0SlowdownWritesTrigger;
	}

	public int getLevel0StopWritesTrigger() {
		return level0StopWritesTrigger;
	}

	public void setLevel0StopWritesTrigger(int level0StopWritesTrigger) {
		this.level0StopWritesTrigger = level0StopWritesTrigger;
	}
}

/**
 * level0 512M
 * level1 512M
 * level2 5G
 * level3 50G
 * level4 500G
 * level5 5T
 */