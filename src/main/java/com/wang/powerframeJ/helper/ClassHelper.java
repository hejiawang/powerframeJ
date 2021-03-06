package com.wang.powerframeJ.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.wang.powerframeJ.annotation.Controller;
import com.wang.powerframeJ.annotation.Service;
import com.wang.powerframeJ.util.ClassUtil;

/**
 * 类操作助手类
 * @author HeJiawang
 * @date   2017.08.02
 */
public final class ClassHelper {
	
	/**
	 * 定义类集合(用于存放所加载的类)
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取应用包名下的所有类
	 * @return
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取应用包名下的所有Service类
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET ) {
			if( cls.isAnnotationPresent(Service.class) ) {
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	
	/**
	 * 获取应用包名下的所有Controller类
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET ) {
			if( cls.isAnnotationPresent(Controller.class) ) {
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	
	/**
	 * 获取应用包名下所有Bean类(包括：Service、Controller 等)
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
	
	/**
	 * 获取应用包名下某父类(或接口)的所有子类(或实现类)
	 * @param superClass 父类(或接口)
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuper( Class<?> superClass ) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET ) {
			if( superClass.isAssignableFrom(cls) && !superClass.equals(cls) ) {
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	
	/**
	 * 获取应用包名下带有某注解的所有类
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation( Class<? extends Annotation> annotationClass ) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET ) {
			if( cls.isAnnotationPresent(annotationClass) ) {
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
}
