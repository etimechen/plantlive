package com.etimechen.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * Controller基类
 * @author chenliang
 *
 */
public class BaseController {
	
	/**
	 * 获取request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();		
		return request;
	}
}
