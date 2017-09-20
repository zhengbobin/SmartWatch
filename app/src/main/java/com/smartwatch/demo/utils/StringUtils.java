package com.smartwatch.demo.utils;




public class StringUtils {


	/**
	 * 删除不是数字的字符串
	 * 
	 * @param callStr
	 * @return
	 */
	public static String deleteNotNum(String callStr) {
		char[] ch = callStr.toCharArray();
		StringBuilder sb = new StringBuilder();
        for (char c : ch) {
            if (c >= '0' && c <= '9')
                sb.append(c);
        }
		return sb.toString();
	}
	
	
	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
}
