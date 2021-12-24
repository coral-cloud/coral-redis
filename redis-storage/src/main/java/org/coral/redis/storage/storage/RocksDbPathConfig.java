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

	public String getDataDb() {
		return getBasePath() + dataDb;
	}

	public String getStringDb() {
		return getDataDb();
	}

	public String getHashDb() {
		return getDataDb();
	}

	public String getListDb() {
		return getDataDb();
	}

	public String getSetDb() {
		return getDataDb();
	}

	public String getZSetDb() {
		return getDataDb();
	}

	public String getExpireDb() {
		return getBasePath() + expireDb;
	}

	public String getMetaDb() {
		return getBasePath() + metaDb;
	}

	public String getBinlogDb() {
		return getBasePath() + binlogDb;
	}


}
