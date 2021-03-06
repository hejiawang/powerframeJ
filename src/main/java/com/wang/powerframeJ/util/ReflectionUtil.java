package com.wang.powerframeJ.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * @author HeJiawang
 * @date   2017.08.03
 */
public final class ReflectionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
	
	/**
	 * 创建实例
	 * @param cls 实例Class
	 * @return
	 */
	public static Object newInstance( Class<?> cls ) {
		Object instance = null ;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error(" new instance failure ", e);
			throw new RuntimeException(e);
		}
		
		return instance;
	}
	
	/**
	 * 调用方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod( Object obj, Method method, Object...args ) {
		Object result = null ;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error(" invoke method failure ", e);
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	/**
	 * 设置成员变量的值<br/>
	 * 要对obj对象中的field初始化为value
	 * @param obj 
	 * @param field
	 * @param value
	 */
	public static void setField( Object obj, Field field, Object value ) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error(" set field failure ", e);
			throw new RuntimeException(e);
		}
	}
}
