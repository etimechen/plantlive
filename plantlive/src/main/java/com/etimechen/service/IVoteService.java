package com.etimechen.service;

import java.util.Map;

public interface IVoteService extends IBaseService<Map<String, Object>> {
	
	public Object insertVote(Map<String, Object> paramMap);
	
	public Object selectVote(Map<String, Object> paramMap);
	
	public Boolean statisticsVoteResult();
}
