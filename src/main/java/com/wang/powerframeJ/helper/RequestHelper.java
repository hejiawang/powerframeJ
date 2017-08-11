package com.wang.powerframeJ.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wang.powerframeJ.bean.FormParam;
import com.wang.powerframeJ.bean.Param;
import com.wang.powerframeJ.util.ArrayUtil;
import com.wang.powerframeJ.util.CodecUtil;
import com.wang.powerframeJ.util.StreamUtil;
import com.wang.powerframeJ.util.StringUtil;

/**
 * 请求助手类
 * @author HeJiawang
 * @date   2017年8月11日
 */
public final class RequestHelper {

	/**
	 * 创建请求对象
	 * @param request
	 * @return
	 */
	public static Param createParam( HttpServletRequest request ) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
		
		return new Param(formParamList);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private static List<FormParam> parseParameterNames( HttpServletRequest request ) {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		Enumeration<String> paramNames = request.getParameterNames();
		while( paramNames.hasMoreElements() ) {
			
			String fieldName = paramNames.nextElement();
			String[] fieldValues = request.getParameterValues(fieldName);
			if( ArrayUtil.isNotEmpty(fieldValues) ) {

				Object fieldValue;
				if( fieldValues.length == 1 ) {
					fieldValue = fieldValues[0];
				} else {
					StringBuilder sb = new StringBuilder("");
					for( int i=0; i<fieldValues.length; i++ ) {
						sb.append(fieldValues[i]);
						if( i != fieldValues.length - 1 ) {
							sb.append(StringUtil.SEPARATOR);
						}
					}
					
					fieldValue = sb.toString();
				}
				
				formParamList.add( new FormParam(fieldName, fieldValue) );
			}
		}
		
		return formParamList;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static List<FormParam> parseInputStream( HttpServletRequest request ) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL( StreamUtil.getString(request.getInputStream()) );
		if( StringUtil.isNotEmpty(body) ) {
			
			String[] kvs = StringUtil.splitString(body, "&");
			if( ArrayUtil.isNotEmpty(kvs) ) {
			
				for( String kv : kvs ) {

					String[] array = StringUtil.splitString(kv, "=");
					if( ArrayUtil.isNotEmpty(array) && array.length == 2 ) {
						
						String fieldName = array[0];
						String fieldValue = array[1];
						formParamList.add( new FormParam(fieldName, fieldValue) );
					}
				}
			}
		}
		
		return formParamList;
	}
}
