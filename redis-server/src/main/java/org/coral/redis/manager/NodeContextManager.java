package org.coral.redis.manager;

import org.helium.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * NodeContextManager
 *
 * @author wuhao
 * @description: manager
 * @createTime 2021/12/11 13:01:00
 */

public class NodeContextManager {
	private static final int MAX_NODE = 1024;

	private static final LinkedHashMap<String, NodeContext> CONTEXT_MAP = new LinkedHashMap<String, NodeContext>() {
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, NodeContext> eldest) {
			return size() > MAX_NODE;
		}
	};

	public static String updateUid(String uid) {
		String id = uid;
		if (StringUtils.isNullOrEmpty(id)) {
			id = UUID.randomUUID().toString().replace("-", "");
		}
		return id;

	}

	public static NodeContext getContext(String uid) {
		return CONTEXT_MAP.get(uid);
	}

	public static void putContext(String uid, NodeContext nodeContext) {
		CONTEXT_MAP.put(uid, nodeContext);
	}

	public static void removeContext(String uid) {
		CONTEXT_MAP.remove(uid);
	}

	public static void updateIndex(String uid, byte[] index) {
		NodeContext nodeContext = getContext(uid);
		nodeContext.setIndex(index);
	}

}
