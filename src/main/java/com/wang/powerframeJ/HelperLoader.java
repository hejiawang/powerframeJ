package com.wang.powerframeJ;

import com.wang.powerframeJ.helper.AopHelper;
import com.wang.powerframeJ.helper.BeanHepler;
import com.wang.powerframeJ.helper.ClassHelper;
import com.wang.powerframeJ.helper.ControllerHelper;
import com.wang.powerframeJ.helper.IocHelper;
import com.wang.powerframeJ.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 * @author HeJiawang
 * @date   2017.08.04
 */
public final class HelperLoader {

	public static void init() {
		Class<?>[] classList = {
				ClassHelper.class,
				BeanHepler.class,
				AopHelper.class,//AopHelper初始化要放在IocHelper之前，因为首先要通过AopHelper获取代理对象，然后才能通过IocHelper进行依赖注入
				IocHelper.class,
				ControllerHelper.class
		};
		
		for( Class<?> cls : classList ) {
			ClassUtil.loadClass(cls.getName(), true);
		}
	}
}
