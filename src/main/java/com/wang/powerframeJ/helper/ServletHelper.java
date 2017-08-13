package com.wang.powerframeJ.helper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet 助手类
 * @author HeJiawang
 * @date   2017年8月13日
 */
public final class ServletHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
	
	/**
	 * 使每个线程独立拥有一份 ServletHelper 实例
	 */
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 初始化
	 * @param request
	 * @param response
	 */
	public static void init( HttpServletRequest request, HttpServletResponse response ) {
		SERVLET_HELPER_HOLDER.set( new ServletHelper(request, response) );
	}
	
	/**
	 * 销毁
	 */
	public static void destroy() {
		SERVLET_HELPER_HOLDER.remove();
	}
	
	/**
	 * 获取 Request 对象
	 * @return
	 */
	private static HttpServletRequest getRequest() {
		return SERVLET_HELPER_HOLDER.get().request;
	}
	
	/**
	 * 获取 Response 对象
	 * @return
	 */
	private static HttpServletResponse getResponse() {
		return SERVLET_HELPER_HOLDER.get().response;
	}
	
	/**
	 * 获取 Session 对象
	 * @return
	 */
	private static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 获取ServletContext对象
	 * @return
	 */
	private static ServletContext getServletContext() {
		return getRequest().getServletContext();
	}
	
	/**
	 * 将属性放入request中
	 * @param key
	 * @param value
	 */
	public static void setRequestAttribute( String key, Object value ) {
		getRequest().setAttribute(key, value);
	}
	
	/**
	 * 从request中获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttribute( String key) {
		return (T) getRequest().getAttribute(key);
	}
	
	/**
	 * 从request中移除属性
	 * @param key
	 */
	public static void remoteRequestAttribute( String key ) {
		getRequest().removeAttribute(key);
	}
	
	/**
	 * 发送重定向相应
	 * @param location
	 */
	public static void sendRedirect( String location ) {
		try {
			getResponse().sendRedirect( getRequest().getContextPath() + location );
		} catch (IOException e) {
			LOGGER.error(" redirect failure ", e);
		}
	}
	
	/**
	 * 将属性放入session中
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute( String key, Object value ) {
		getSession().setAttribute(key, value);
	}
	
	/**
	 * 从session中获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute( String key ) {
		return (T) getRequest().getSession().getAttribute(key);
	}
	
	/**
	 * 从session中移除属性
	 * @param key
	 */
	public static void removeSessionAttribute( String key ) {
		getRequest().getSession().removeAttribute(key);
	}

	/**
	 * 使session失效
	 */
	public static void invalidateSession() {
		getRequest().getSession().invalidate();
	}
}
