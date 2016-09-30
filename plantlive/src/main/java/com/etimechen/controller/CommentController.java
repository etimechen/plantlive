package com.etimechen.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etimechen.component.util.CommonUtil;
import com.etimechen.component.util.GetCityByIp;
import com.etimechen.service.ICommentService;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	
	@Resource
	private ICommentService commentService;
	
	/**
	 * 分页查询评论
	 * @param paramMap
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value="/selectcomment", method=RequestMethod.GET)
	public Object selectCommentByLimit(@RequestParam Map<String, Object> paramMap){
		return this.commentService.selectCommentByLimit(paramMap);
	}
	
	/**
	 * 提交评论
	 * @param paramMap
	 * @param request
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value="/insertcomment", method=RequestMethod.POST)
	public Object insertComment(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String ipAddr = CommonUtil.getIpAddr(request);
		Date date = new Date();		
		paramMap.put("commentcity", GetCityByIp.GetAddressByIp(ipAddr));
		paramMap.put("commentip", ipAddr);
		paramMap.put("commentdatetime", date);
		return this.commentService.insertComment(paramMap);
	}
}
