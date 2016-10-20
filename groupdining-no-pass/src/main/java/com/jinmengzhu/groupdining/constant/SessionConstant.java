package com.jinmengzhu.groupdining.constant;


/**
 * 职责：session中的常亮 
 * 使用方法：
 */
public class SessionConstant {
	
	/**session清单*/
	public static String KEY_CURRENT_ADMIN = "KEY_CURRENT_ADMIN";//后台用户对象
	
	public static String KEY_CURRENT_LOGINNAME = "KEY_CURRENT_LOGINNAME";//用户名称
	
	public static String KEY_CURRENT_USERNAME = "KEY_CURRENT_USERNAME";//用户名称
	
	public static String KEY_CURRENT_USERID = "KEY_CURRENT_USERID";//用户主键
	
	public static String KEY_CURRENT_USER = "KEY_CURRENT_USER";//用户
	
	public static String KEY_CURRENT_ADMINID = "KEY_CURRENT_ADMINID";//用户主键
	
	public static String KEY_CURRENT_ADMINTYPE = "KEY_CURRENT_ADMINTYPE";//用户类型
	
	public static String KEY_CURRENT_ADMIN_REALNAME = "KEY_CURRENT_ADMIN_REALNAME";//用户真实姓名
	
	public static String KEY_CHECKCODE = "KEY_CHECKCODE";//验证码
	
	public static String KEY_CURRENT_ROLEID = "KEY_CURRENT_ROLEID";//当前用户角色
	
	public static String KEY_APP_LOGIN_USER = "KEY_APP_LOGIN_USER";//用户主键
	
	/**session最大过期时间*/
	public static final int MAXACTIVE_TIME = 30*60;
	
}
