package org.coral.redis.storage;

import java.io.File;
import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-02 13:50:00
 */
public class FileUtils {
	/**
	 * checkAndCreateDir
	 *
	 * @param path
	 */
	public static void checkAndCreateDir(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
