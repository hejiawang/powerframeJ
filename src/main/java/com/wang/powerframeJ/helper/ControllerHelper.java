package com.wang.powerframeJ.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wang.powerframeJ.annotation.Action;
import com.wang.powerframeJ.bean.Handler;
import com.wang.powerframeJ.bean.Request;
import com.wang.powerframeJ.util.ArrayUtil;
import com.wang.powerframeJ.util.CollectionUtil;

/**
 * 控制器助手类
 * @author HeJiawang
 * @date   2017.08.04
 */
public final class ControllerHelper {
	
	/**
	 * 用于存放请求与处理器的映射关系(简称 Action Map)
	 */
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static {
		//获取所有的 Controller 类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if( CollectionUtil.isNotEmpty(controllerClassSet) ) {

			//遍历这些 Controller 类
			for( Class<?> controllerClass : controllerClassSet ) {
				
				//获取 Controller 类中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if( ArrayUtil.isNotEmpty(methods) ) {
					
					//遍历这些 Controller 类中的方法
					for( Method method : methods ) {
						
						//判断当前方法是否带有 Action 注解
						if( method.isAnnotationPresent(Action.class) ) {
							
							//从 Action 注解中获取URL映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							
							//验证URL规则
							if( mapping.matches("\\w+:/\\w*") ) {	//例：   get:/testUrl
								
								String[] array = mapping.split(":");
								if( ArrayUtil.isNotEmpty(array) && array.length==2 ) {
									
									//获取请求方法与请求路径
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									
									//初始化 ACTION_MAP
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取 Handler
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler( String requestMethod, String requestPath ) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
