package com.etimechen.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.etimechen.dao.ILiverecordDao;
import com.etimechen.service.ILiverecordService;

public class LiverecordService implements ILiverecordService {

	@Resource
	private ILiverecordDao liverecordDao;
	
	@Override
	public void insertLiverecord(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		this.liverecordDao.insertLiverecordSelective(paramMap);
	}

}
