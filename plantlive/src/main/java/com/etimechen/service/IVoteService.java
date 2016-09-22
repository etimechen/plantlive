package com.etimechen.service;

import java.util.Map;

public interface IVoteService extends IBaseService<Map<String, Object>> {
	public Integer insertVote(Map<String, Object> map);
}
