package com.etimechen.service;

import java.util.Map;

public interface IVoteService extends IBaseService<Map<String, Object>> {
	public Object insertvote(Map<String, Object> map);
}
