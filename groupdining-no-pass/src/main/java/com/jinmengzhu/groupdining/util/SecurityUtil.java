package com.jinmengzhu.groupdining.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 职责：用户密码加密 使用名称+密码增加盐值 使用方法：
 * 
 */
public class SecurityUtil {
	public static String md5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	public static String md5(String username, String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(username.getBytes());
		md.update(password.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String temp2 = "123456";
		System.out.println(md5("admin", temp2));
	}

	public static final String KEY = "7w3jT0OwXcSAddU93yYLUdVRSl3+7BxU";
	/**
	 * 加密数据
	 * 
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		String result = "";
		try {
			String str = value;
			byte[] key = Base64.decodeBase64(KEY);
			byte[] data = DESede.encrypt(str.getBytes(), key);
			return Base64.encodeBase64String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	/**
	 * 解密数据
	 * 
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		String result = "";
		try {
			byte[] str = Base64.decodeBase64(value);
			byte[] key = Base64.decodeBase64(KEY);
			byte[] data = DESede.decrypt(str, key);
			return new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void setFileDownloadHeader(HttpServletRequest request,
			HttpServletResponse response, String fileName) {
		final String userAgent = request.getHeader("USER-AGENT");
        try {
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {
        }
	}
}
