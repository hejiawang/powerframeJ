package com.wang.powerframeJ;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wang.powerframeJ.bean.Data;
import com.wang.powerframeJ.bean.Handler;
import com.wang.powerframeJ.bean.Param;
import com.wang.powerframeJ.bean.View;
import com.wang.powerframeJ.helper.BeanHepler;
import com.wang.powerframeJ.helper.ConfigHelper;
import com.wang.powerframeJ.helper.ControllerHelper;
import com.wang.powerframeJ.helper.RequestHelper;
import com.wang.powerframeJ.helper.UploadHelper;
import com.wang.powerframeJ.util.ArrayUtil;
import com.wang.powerframeJ.util.CodecUtil;
import com.wang.powerframeJ.util.JsonUtil;
import com.wang.powerframeJ.util.ReflectionUtil;
import com.wang.powerframeJ.util.StreamUtil;
import com.wang.powerframeJ.util.StringUtil;

/**
 * 请求转发器
 * @author HeJiawang
 * @date   2017.08.04
 */
@WebServlet( urlPatterns = "/*", loadOnStartup = 0 )
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	@Override
	public void init( ServletConfig servletConfig ) throws ServletException {
		//初始化相关helper类
		HelperLoader.init();
		//获取 ServletContext 对象(用户注册servlet)
		ServletContext servletContext = servletConfig.getServletContext();
		//注册处理 jsp 的 servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		//注册处理静态资源的默认servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		
		UploadHelper.init(servletContext);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求方法与请求路径
		String requestMehtod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();
		
		if( requestPath.equals("/favicon.ico") ) {
			return ;
		}
		
		//获取 Action 处理器
		Handler handler = ControllerHelper.getHandler(requestMehtod, requestPath);
		if( handler != null ) {
			
			//获取 Controller 类及其 Bean 实例
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHepler.getBean(controllerClass);
			
			//创建请求参数对象
			Param param;
			if( UploadHelper.isMultipart(request) ) {
				param = UploadHelper.createParam(request);
			} else {
				param = RequestHelper.createParam(request);
			}
			
			//调用 Action 方法
			Method actionMethod = handler.getActionMethod();
			
			Object result ;
			if( param.isEmpty() ) {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			} else {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			}
			
			//处理 Action 方法返回值
			if( result instanceof View ) {
				handleViewResult( (View)result, request, response );
			} else if( result instanceof Data ) {
				handleDataResult( (Data) result, response );
			}
		}
	}

	/**
	 * 返回 JSON 数据
	 * @param data
	 * @param response
	 * @throws IOException
	 */
	private void handleDataResult( Data data, HttpServletResponse response ) throws IOException {
		Object model = data.getModel();
		if( model != null ) {
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter write = response.getWriter();
			String json = JsonUtil.toJson(model);
			write.write(json);
			write.flush();
			write.close();
		}
	}
	
	/**
	 * 返回页面
	 * @param view
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleViewResult( View view, HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
		String path = view.getPath();
		if( StringUtil.isNotEmpty(path) ) {
			
			if( path.startsWith("/") ) {
				
				response.sendRedirect( request.getContextPath() + path );
			} else {
				
				Map<String, Object> model = view.getModel();
				for( Map.Entry<String, Object> entry : model.entrySet() ) {
					
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
			}
		}
	}
	
}
