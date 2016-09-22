package com.etimechen.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etimechen.dao.IVoteDao;
import com.etimechen.service.IVoteService;

@Service
public class VoteService implements IVoteService {

	@Autowired
	private IVoteDao voteDao;
	
	@Override
	public Integer insertVote(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return this.voteDao.vote(paramMap);
	}

}
