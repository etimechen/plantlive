package com.etimechen.service;

import java.util.Map;

public interface ICommentService extends IBaseService<Map<String, Object>> {

	public Object selectCommentByLimit(Map<String, Object> paramMap);
	
	public Object insertComment(Map<String, Object> paramMap);
	
}
