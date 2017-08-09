package com.wang.powerframeJ.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理管理器
 * @author HeJiawang
 * @date   2017.08.06
 */
public class ProxyManager {

	/**
	 * 创建代理对象
	 * @param targetClass 目标类
	 * @param proxyList 一组proxy接口的实现
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createProxy( final Class<T> targetClass, final List<Proxy> proxyList ) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {

			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
			}
			
		});
	}
}
