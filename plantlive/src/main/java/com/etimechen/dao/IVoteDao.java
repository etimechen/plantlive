package com.etimechen.dao;

import java.util.Date;
import java.util.Map;

public interface IVoteDao extends IBaseDao<Map<String, Object>> {
	public Integer selectVoteCountByIpAndDate(Map<String, Object> map);
	public Map<String, Object> selectVoteResultByDate(Date date);
	public Integer insertVoteSelective(Map<String, Object> map);
}
