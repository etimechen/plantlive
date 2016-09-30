package com.etimechen.service;

import java.util.Map;

public interface ILiverecordService extends IBaseService<Map<String, Object>> {
	public void insertLiverecord(Map<String, Object> paramMap);
}
