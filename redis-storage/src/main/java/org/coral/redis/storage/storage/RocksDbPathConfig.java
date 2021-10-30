package org.coral.redis.storage.storage;

/**
 *
 * @author wuhao
 * @createTime 2021-07-27 11:38:00
 */
public class RocksDbPathConfig {
	private String basePath = "./";
	private String stringDb = "rocksdb/dbstring/";
	private String hashDb = "rocksdb/dbhash/";
	private String listDb = "rocksdb/dblist/";
	private String setDb = "rocksdb/dbset/";
	private String zsetDb = "rocksdb/dbzset/";
	private String expireDb = "rocksdb/expire/";
	private String metaDb = "rocksdb/meta/";
	private String binlogDb = "rocksdb/binlog/";

	public static RocksDbPathConfig instance = new RocksDbPathConfig();

	public static RocksDbPathConfig getInstance() {
		return instance;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getStringDb() {
		return basePath + stringDb;
	}

	public String getHashDb() {
		return basePath + hashDb;
	}

	public String getListDb() {
		return basePath + listDb;
	}

	public String getSetDb() {
		return basePath + setDb;
	}

	public String getZsetDb() {
		return basePath + zsetDb;
	}

	public String getExpireDb() {
		return basePath + expireDb;
	}

	public String getMetaDb() {
		return basePath + metaDb;
	}

	public String getBinlogDb() {
		return basePath + binlogDb;
	}
}
