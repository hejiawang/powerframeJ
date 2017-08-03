package com.wang.powerframeJ.helper;

import java.lang.reflect.Field;
import java.util.Map;

import com.wang.powerframeJ.annotation.Inject;
import com.wang.powerframeJ.util.ArrayUtil;
import com.wang.powerframeJ.util.CollectionUtil;
import com.wang.powerframeJ.util.ReflectionUtil;

/**
 * 依赖注入助手类
 * @author HeJiawang
 * @date   2017.08.02
 */
public final class IocHelper {

	static {
		//获取所有的Bean类与Bean实例之间的映射关系(简称Bean Map)
		Map<Class<?>, Object> beanMap = BeanHepler.getBeanMap();
		if( CollectionUtil.isNotEmpty(beanMap) ) {
			
			//遍历Bean Map
			for( Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet() ) {
				//从Bean Map 中获取Bean类与Bean实体
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				
				//获取Bean类定义的所有成员变量(简称 Bean Field)
				Field[] beanFields = beanClass.getDeclaredFields();
				if( ArrayUtil.isNotEmpty(beanFields) ) {
					
					//遍历 Bean Field
					for( Field beanField : beanFields ) {
						
						//判断当前Bean Field是否带有Inject注解
						if( beanField.isAnnotationPresent(Inject.class) ) {
							
							//在Bean Map中获取Bean Field对应的实例
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if( beanFieldInstance != null ) {
								
								//通过反射初始化BeanField的值
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
