package com.wang.powerframeJ.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类 
 * @author HeJiawang
 * @date   2017.07.31
 */
public final class StringUtil {
	
	public static final String SEPARATOR = String.valueOf( (char) 29 );
	
	/**
	 * 判断字符串是否为kong
	 * @param str String
	 * @return
	 */
	public static boolean isEmpty( String str ) {
		if( str != null ) {
			str = str.trim();
		}
		
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 判断字符串是否非空
	 * @param str String
	 * @return
	 */
	public static boolean isNotEmpty( String str ) {
		return !isEmpty(str);
	}
	
	/**
	 * 分割字符串
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String[] splitString( String str, String mark ) {
		return str.split(mark);
	}
}
