package org.coral.redis.uils;

import org.helium.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuhao
 * @description: IdUtils
 * @createTime 2021/12/04 22:57:00
 */

public class IdUtils {

	public static Map<String, String> idMaps = new LinkedHashMap<String, String>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return size() > 1024;
		}
	};

	public static String getId(String sign) {
		String id = idMaps.get(sign);
		if (StringUtils.isNullOrEmpty(id)) {
			id = UUID.randomUUID().toString().replace("-", "");
			idMaps.put(sign, id);
		}
		return id;

	}

	public static void main(String[] args) {

	}
}
