package com.wang.powerframeJ.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理 </br>
 * 设计模式——模板
 * @author HeJiawang
 * @date   2017.08.06
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		
		try {
			if( intercept(cls, method, params) ) {
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params, result);
			}
		} catch (Exception e) {
			LOGGER.error(" proxy failure ", e);
			error( cls, method, params, e );
			throw e;
		} finally {
			end();
		}
		
		return result;
	}
	
	public void begin() {
		
	}
	
	public boolean intercept( Class<?> cls, Method method, Object[] params ) throws Throwable {
		return true;
	}
	
	public void before( Class<?> cls, Method method, Object[] params ) throws Throwable {
		
	}
	
	public void after( Class<?> cls, Method method, Object[] params, Object result ) throws Throwable {
		
	}
	
	public void error( Class<?> cls, Method method, Object[] params, Throwable e ) {
		
	}
	
	public void end() {
		
	}
	
}
