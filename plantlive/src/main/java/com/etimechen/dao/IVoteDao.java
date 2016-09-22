package com.etimechen.dao;

import java.util.Map;

public interface IVoteDao extends IBaseDao<Map<String, Object>> {
	public Integer vote(Map<String, Object> map);
}
