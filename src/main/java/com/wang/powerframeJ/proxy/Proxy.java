package com.wang.powerframeJ.proxy;

/**
 * 代理接口
 * @author HeJiawang
 * @date   2017.08.06
 */
public interface Proxy {
	
	/**
	 * 执行链式代理
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy( ProxyChain proxyChain ) throws Throwable;
}
