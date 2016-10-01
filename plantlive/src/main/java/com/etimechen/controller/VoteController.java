package com.etimechen.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 * 投票-Controller
 * 
 * @author chenliang
 *
 */
@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController {

	@Resource
	private IVoteService voteService;

	/**
	 * 投票
	 * 
	 * @param paramMap
	 * @param request
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value = "/insertvote", method = RequestMethod.POST)
	public Object insertvote(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
		String ipAddr = CommonUtil.getIpAddr(request);
		String excuteTime = Configurator.EXCUTE_TIME;
		if (StringUtils.isEmpty(excuteTime)) {
			// 不设置值默认为下午5点
			excuteTime = "17:00";
		}
		Date date = CommonUtil.getCurrentVoteDate(excuteTime);
		paramMap.put("voteip", ipAddr);
		paramMap.put("votedate", date);
		return this.voteService.insertVote(paramMap);
	}

	/**
	 * 获取投票数
	 * 
	 * @param paramMap
	 * @param request
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value = "/selectvote", method = RequestMethod.GET)
	public Object selectvote() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date todayDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todayDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		paramMap.put("today", todayDate);
		paramMap.put("tomorrow", calendar.getTime());
		return this.voteService.selectVote(paramMap);
	}

}
