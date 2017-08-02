package com.wang.powerframeJ.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * 集合工具类 
 * @author HeJiawang
 * @date   2017.07.31
 */
public final class CollectionUtil {
	
	/**
	 * 判断 Collection 是否为空
	 * @param collection Collection
	 * @return
	 */
	public static boolean isEmpty( Collection<?> collection ) {
		return CollectionUtils.isEmpty(collection);
	}
	
	/**
	 * 判断 Collection 是非空
	 * @param collection Collection
	 * @return
	 */
	public static boolean isNotEmpty( Collection<?> collection ) {
		return !isEmpty(collection);
	}
	
	/**
	 * 判断 map 是否为空
	 * @param map Map
	 * @return
	 */
	public static boolean isEmpty( Map<?, ?> map ) {
		return MapUtils.isEmpty(map);
	}
	
	/**
	 * 判断 map 是否非空
	 * @param map Map
	 * @return
	 */
	public static boolean isNotEmpty( Map<?, ?> map ) {
		return !isEmpty(map);
	}
}
