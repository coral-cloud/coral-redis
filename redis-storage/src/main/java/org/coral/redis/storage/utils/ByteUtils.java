package org.coral.redis.storage.utils;

import java.nio.charset.StandardCharsets;

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


	/**
	 * double转byte[]
	 *
	 * @param value
	 * @return byte[]
	 */

	public static byte[] doubleToByteArray(double value) {
		long bitLayoutLongValue = Double.doubleToLongBits(value);
		byte[] bytes = new byte[8];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bitLayoutLongValue >> (bytes.length - i - 1) * 8);
		}
		return bytes;

	}

	/**
	 * byte[]转double
	 *
	 * @param bytes
	 * @return double
	 */

	public static double byteArrayToDouble(byte[] bytes) {
		long ff = 0xFF;
		long bitLayoutLongValue = 0;
		for (int i = 0; i < bytes.length; i++) {
			bitLayoutLongValue |= (bytes[i] & ff) << (bytes.length - i - 1) * 8;
		}
		return Double.longBitsToDouble(bitLayoutLongValue);

	}

	/**
	 * stringToBytes
	 *
	 * @param str
	 * @return
	 */
	public static byte[] stringToBytes(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * byteToString
	 *
	 * @param bytes
	 * @return
	 */
	public static String byteToString(byte[] bytes) {
		return new String(bytes);
	}

}
