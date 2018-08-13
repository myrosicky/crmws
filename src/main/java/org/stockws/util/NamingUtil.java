package org.stockws.util;



public class NamingUtil {
	public static final String userName = "userName";
	public static final String userID = "userID";
	/**
	 * current speaking user who send message
	 */
	public static  final String currentSUID = "userId_speak";
	/**
	 * current listening user who receive message
	 */
	public static final String currentLUID = "userId_listen";

	public  static final int FETCHALL = 1, FETCHEXPLICIT = 2;
	
	public static final String sk_users = "users", sk_user_format = "user%1$x", sk_userName_format = "%1$s", sk_onlineUsers = "ol:u", 
			sk_sessionUser_format = "se:%1$s"
			,sk_dicts_format = "dict:%1$s"
			;
	public static final String cookieId = "cid";
	
	public static String genStoreKey(String formatPattern, Object value) {
		return String.format(formatPattern, value);
	}

}
