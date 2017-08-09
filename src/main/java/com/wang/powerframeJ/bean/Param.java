package com.wang.powerframeJ.bean;

import java.util.Map;

import com.wang.powerframeJ.util.CastUtil;
import com.wang.powerframeJ.util.CollectionUtil;

/**
 * 请求参数对象
 * @author HeJiawang
 * @date   2017.08.04
 */
public class Param {

	private Map<String, Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	/**
	 * 根据参数名获取 long 型参数值
	 * @param name
	 * @return
	 */
	public long getLong( String name ) {
		return CastUtil.castLong(paramMap.get(name));
	}
	
	/**
	 * 根据参数名获取 String 型参数值
	 * @param name
	 * @return
	 */
	public String getString( String name ) {
		return CastUtil.castString(paramMap.get(name));
	}
	
	/**
	 * 根据参数名获取 double 型参数值
	 * @param name
	 * @return
	 */
	public double getDouble( String name ) {
		return CastUtil.castDouble(paramMap.get(name));
	}
	
	/**
	 * 根据参数名获取 int 型参数值
	 * @param name
	 * @return
	 */
	public int getInt( String name ) {
		return CastUtil.castInt(paramMap.get(name));
	}
	
	/**
	 * 获取所有字段信息
	 * @return
	 */
	public Map<String, Object> getMap(){
		return paramMap;
	}
	
	/**
	 * 验证参数是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return CollectionUtil.isEmpty(paramMap);
	}
	
}
