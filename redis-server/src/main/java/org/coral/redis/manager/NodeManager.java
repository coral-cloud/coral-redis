package org.coral.redis.manager;

import org.coral.redis.storage.expire.RcpStorageSynTask;
import org.helium.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuhao
 * @description: manager
 * @createTime 2021/12/11 13:01:00
 */

public class NodeManager {
	private static final int MAX_NODE = 1024;

	private static LinkedHashMap<String, byte[]> indexMap = new LinkedHashMap<String, byte[]>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
			return size() > MAX_NODE;
		}
	};

	private static LinkedHashMap<String, RcpStorageSynTask> pynMap = new LinkedHashMap<String, RcpStorageSynTask>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, RcpStorageSynTask> eldest) { return size() > MAX_NODE; }
	};

	private static LinkedHashMap<String, String> idMaps = new LinkedHashMap<String, String>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) { return size() > MAX_NODE;
		}
	};

	public static String getUid(String sign) {
		String id = idMaps.get(sign);
		if (StringUtils.isNullOrEmpty(id)) {
			id = UUID.randomUUID().toString().replace("-", "");
		}
		idMaps.put(sign, id);
		return id;

	}


	public static byte[] getIndex(String id) {
		return indexMap.get(id);
	}

	public static void setIndex(String id, byte[] index) {
		indexMap.put(id, index);
	}

	public static boolean existIndex(String id) {
		return indexMap.containsKey(id);
	}


	public static RcpStorageSynTask getTask(String id) {
		return pynMap.get(id);
	}

	public static void setTask(String id, RcpStorageSynTask rcpStorageSynTask) {
		pynMap.put(id, rcpStorageSynTask);
	}

	public static boolean existTask(String id) {
		return pynMap.containsKey(id);
	}
}
