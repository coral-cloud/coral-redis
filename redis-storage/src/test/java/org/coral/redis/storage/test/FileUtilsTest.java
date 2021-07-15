package org.coral.redis.storage.test;

import org.coral.redis.storage.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wuhao
 * @createTime 2021-07-02 13:57:00
 */
public class FileUtilsTest {
	@Test
	public void testFile() throws IOException {
		String filePath = "temp/filedir";
		FileUtils.checkAndCreateDir(filePath);
		assertEquals(true, new File(filePath).exists());

	}
}
