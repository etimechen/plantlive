package com.etimechen.component.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.etimechen.component.constant.MessageConstant;
import com.etimechen.component.util.CommonUtil;

public class MethodParamInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String[]> map;
		Map<String, Object> resultMap;
		String requestMethod = request.getMethod().toLowerCase();
		if("post".equals(requestMethod) || "put".equals(requestMethod) || "delete".equals(requestMethod)){
			map = request.getParameterMap();
			if(map.isEmpty()){
				resultMap = new HashMap<String, Object>();
				resultMap.put(MessageConstant.MSG, MessageConstant.PARAMNULL);
				CommonUtil.responseOutWithJSON(response, resultMap);
				return false;
			}
		}
		return true;
	}


}
