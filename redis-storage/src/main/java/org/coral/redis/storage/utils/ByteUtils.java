package org.coral.redis.storage.utils;

import org.coral.redis.storage.entity.RcpZSetMtsKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-15 11:44:00
 */
public class ByteUtils {
	/**
	 * int到byte[] 由高位到低位
	 *
	 * @param i 需要转换为byte数组的整行值。
	 * @return byte数组
	 */

	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;

	}

	/**
	 * byte[]转int
	 *
	 * @param bytes 需要转换成int的数组
	 * @return int值
	 */

	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (3 - i) * 8;
			value += (bytes[i] & 0xFF) << shift;
		}
		return value;

	}

	public static RcpZSetMtsKey parseBytes(byte[] content) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
		inputStream.read();
		byte[] keyLengthBytes = new byte[4];
		System.arraycopy(content, 0, keyLengthBytes, 0, 4);
		int keyLength = ByteUtils.byteArrayToInt(keyLengthBytes);
		byte[] keyBytes = new byte[keyLength];
		System.arraycopy(content, 4, keyBytes, 0, keyLength);
		byte[] versionBytes = new byte[4];
		System.arraycopy(content, 4 + keyLength, versionBytes, 0, 4);
		int version = ByteUtils.byteArrayToInt(versionBytes);
		int memberLength = content.length - 8 - keyLength;
		byte[] memberBytes = new byte[memberLength];
		System.arraycopy(content, content.length - memberLength, memberBytes, 0, memberLength);
		return RcpZSetMtsKey.build(keyBytes, version, memberBytes);
	}
	public byte[] getBytes(){
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		try {
//			outputStream.write(ByteUtils.intToByteArray(key.length));
//			outputStream.write(key);
//			outputStream.write(ByteUtils.intToByteArray(version));
//			outputStream.write(member);
//		} catch (IOException e) {
//			LOGGER.error("getKey error:{} ", new String(key), e);
//		}
//		return outputStream.toByteArray();
		return null;

	}
}
