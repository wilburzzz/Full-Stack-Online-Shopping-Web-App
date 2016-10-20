package com.jinmengzhu.groupdining.util;

import java.util.Random;

/**
 * 职责：字符工具 用于判空截取等操作 使用方法：
 * 
 */
public class StringUtil {

	/**
	 * 功能描述：判断是否为空 使用方法： 使用约束： 逻辑：
	 * 
	 * @param str
	 */
	public static boolean isNull(String str) {

		if (str == null || str.equals("null") || str.equals("") || isBlank(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 功能描述： 使用方法： 使用约束： 逻辑：
	 * 
	 * @param str
	 */
	public static boolean isBlank(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}

	public static String toUtf8String(String s) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 生成6位数字随机数（注册验证码用）
	 * 
	 * @return
	 */
	public static String createRandom() {
		String strRandom = "";
		for (int i = 0; i < 6; i++) {
			strRandom += String.valueOf(new Random().nextInt(10));
		}
		return strRandom;
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String ReturnNotNull(Object inputValue) {
		String strValue = "";
		if (inputValue != "" && inputValue != null&&!inputValue.equals(""))
			return inputValue.toString();
		else {
			return strValue;
		}

	}
	
	/**
	 * 手机号码屏蔽关键数字,屏蔽4-10位
	 * @description description
	 */
	public static String encryptPhoneNum(String phoneNum){
		StringBuffer s = new StringBuffer();
		for (int i=0;i<phoneNum.length();i++) {
			if (i>2&&i<9) {
				s.append("*");
				continue;
			}
			s.append(phoneNum.toCharArray()[i]);
		}
		return  s.toString();
	}
	
//	public static String getSex(String inputString)
//	{
//		string
//	}
}
