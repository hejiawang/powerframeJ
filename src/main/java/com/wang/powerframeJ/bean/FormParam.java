package com.wang.powerframeJ.bean;

/**
 * 封装表单参数
 * @author HeJiawang
 * @date   2017年8月10日
 */
public class FormParam {

	private String fieldName;
	private Object fieldValue;
	
	public FormParam(String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
	
}
