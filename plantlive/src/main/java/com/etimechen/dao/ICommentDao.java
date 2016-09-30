package com.etimechen.dao;

import java.util.List;
import java.util.Map;

public interface ICommentDao extends IBaseDao<Map<String, Object>> {

	public List<Object> selectCommentByLimit(Map<String, Integer> paramMap);
	
	public int selectCommentCountByLimit();
	
	public int insertCommentSelective(Map<String, Object> paramMap);
	
}
