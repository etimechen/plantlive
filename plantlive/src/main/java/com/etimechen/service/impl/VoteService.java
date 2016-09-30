package com.etimechen.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etimechen.component.constant.MessageConstant;
import com.etimechen.dao.IVoteDao;
import com.etimechen.service.IVoteService;

@Service
public class VoteService implements IVoteService {

	@Resource
	private IVoteDao voteDao;

	@Override
	public Object insertvote(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();	
		Integer voteCount = this.voteDao.selectVoteCountByIpAndDate(paramMap);
		if(voteCount > 0){
			map.put(MessageConstant.SUCCESS, false);
			map.put(MessageConstant.MSG, "record already exists");			
		}else{
			if(paramMap.get("voteyesorno")!=null){
				this.voteDao.insertVoteSelective(paramMap);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap = this.voteDao.selectVoteResultByDate((Date)paramMap.get("votedate"));
				map.put(MessageConstant.SUCCESS, true);
				map.put(MessageConstant.RESULTS, resultMap);
			}else{
				map.put(MessageConstant.SUCCESS, false);
				map.put(MessageConstant.MSG, "param error");
			}
		}
		return map;
	}

	@Override
	public Object selectvote(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = this.voteDao.selectVoteResultByDate((Date)paramMap.get("votedate"));
		map.put(MessageConstant.SUCCESS, true);
		map.put(MessageConstant.RESULTS, resultMap);
		return map;
	}
}
