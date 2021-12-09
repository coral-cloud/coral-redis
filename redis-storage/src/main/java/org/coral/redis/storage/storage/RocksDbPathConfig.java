package org.coral.redis.storage.storage;


/**
 * @author wuhao
 * @createTime 2021-07-27 11:38:00
 */
public class RocksDbPathConfig {
	private String basePath = "";
	private String dataDb = "/rocksdb/db/";
	private String expireDb = "/rocksdb/expire/";
	private String metaDb = "/rocksdb/meta/";
	private String binlogDb = "/rocksdb/binlog/";


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
		return getBasePath() + dataDb;
	}

	public String getHashDb() {
		return getStringDb();
	}

	public String getListDb() {
		return getStringDb();
	}

	public String getSetDb() {
		return getStringDb();
	}

	public String getZsetDb() {
		return getStringDb();
	}

	public String getExpireDb() {
		return getBasePath() + expireDb;
	}

	public String getMetaDb() {
		return getBasePath() + metaDb;
	}

	public String getBinlogDb() {
		return getStringDb();
	}


}
