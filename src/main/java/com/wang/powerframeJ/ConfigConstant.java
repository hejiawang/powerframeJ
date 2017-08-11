package com.wang.powerframeJ;

/**
 * 提供相关配置项常量
 * @author HeJiawang
 * @date   2017.08.02
 */
public interface ConfigConstant {

	String CONFIG_FILE = "powerFrameJ/powerFrameJ.properties";
	
	String JDBC_DIVER  = "powerFrameJ.jdbc.driver";
	String JDBC_URL  = "powerFrameJ.jdbc.url";
	String JDBC_USERNAME  = "powerFrameJ.jdbc.username";
	String JDBC_PASSWORD  = "powerFrameJ.jdbc.password";
	
	String APP_BASE_PACKAGE = "powerFrameJ.app.base_package";
	String APP_JSP_PATH = "powerFrameJ.app.jsp_path";
	String APP_ASSET_PATH = "powerFrameJ.app.asset_path";
	
	String APP_UPLOAD_LIMIT = "powerFrameJ.app.upload_limit";
}
