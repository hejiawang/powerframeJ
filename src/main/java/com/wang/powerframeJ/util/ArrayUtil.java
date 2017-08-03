package com.wang.powerframeJ.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * @author HeJiawang
 * @date   2017.08.02
 */
public class ArrayUtil {

	/**
	 * 判断数组是否非空
	 * @param array 数组
	 * @return
	 */
	public static boolean isNotEmpty( Object[] array ) {
		return ArrayUtils.isNotEmpty(array);
	}
	
	/**
	 * 判断数组是否为空
	 * @param array 数组
	 * @return
	 */
	public static boolean isEmpty( Object[] array ) {
		return ArrayUtils.isEmpty(array);
	}
}
