package com.etimechen.dao;

import java.util.Map;

public interface ILiverecordDao extends IBaseDao<Map<String, Object>> {
	public Integer insertLiverecordSelective(Map<String, Object> paramMap);
}
