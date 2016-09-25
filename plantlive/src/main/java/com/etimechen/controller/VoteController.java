package com.etimechen.controller;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etimechen.component.Configurator;
import com.etimechen.component.util.CommonUtil;
import com.etimechen.service.IVoteService;

@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController {
	
	@Resource
	private IVoteService voteService;
	
	@ResponseBody
	@RequestMapping(value="/insertvote", method=RequestMethod.POST)
	public Object insertvote(@RequestParam Map<String, Object> paramMap, HttpServletRequest request){
		String ipAddr = CommonUtil.getIpAddr(request);		
		String excuteTime = Configurator.EXCUTE_TIME;
		if (StringUtils.isEmpty(excuteTime)) {
			excuteTime = "17:00";
		}
		Date date = CommonUtil.getCurrentVoteDate(excuteTime);
		paramMap.put("voteip", ipAddr);
		paramMap.put("votedate", date);
		return this.voteService.insertvote(paramMap);
	}
	
}
