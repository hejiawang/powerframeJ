package com.wang.powerframeJ;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求转发器
 * @author HeJiawang
 * @date   2017.08.04
 */
@WebServlet( urlPatterns = "/*", loadOnStartup = 0 )
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO 
	}
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO 
	}

}
