package com.etimechen.controller;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etimechen.service.IVoteService;

@Controller
@RequestMapping("/")
public class VoteController extends BaseController {
	
	@Resource
	private IVoteService voteService;
	
	@ResponseBody
	@RequestMapping("/vote")
	public Object vote(Map<String, Object> paramMap){
		return this.voteService.insertVote(paramMap);
	}
}
