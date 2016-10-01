package com.etimechen.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etimechen.component.constant.MessageConstant;
import com.etimechen.component.util.CommonUtil;
import com.etimechen.dao.ILiverecordDao;
import com.etimechen.dao.IVoteDao;
import com.etimechen.service.IVoteService;

@Service
public class VoteService implements IVoteService {

	@Resource
	private IVoteDao voteDao;
	
	@Resource
	private ILiverecordDao liverecordDao;

	@Override
	public Object insertVote(Map<String, Object> paramMap) {
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
				Date todayDate = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(todayDate);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Map<String, Object> todayMap = new HashMap<String, Object>();
				Map<String, Object> tomorrowMap = new HashMap<String, Object>();
				todayMap = this.voteDao.selectVoteResultByDate(todayDate);
				tomorrowMap = this.voteDao.selectVoteResultByDate(calendar.getTime());
				resultMap.put("today", todayMap);
				resultMap.put("tomorrow", tomorrowMap);
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
	public Object selectVote(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> todayMap = new HashMap<String, Object>();
		Map<String, Object> tomorrowMap = new HashMap<String, Object>();
		todayMap = this.voteDao.selectVoteResultByDate((Date)paramMap.get("today"));
		tomorrowMap = this.voteDao.selectVoteResultByDate((Date)paramMap.get("tomorrow"));
		resultMap.put("today", todayMap);
		resultMap.put("tomorrow", tomorrowMap);
		map.put(MessageConstant.SUCCESS, true);
		map.put(MessageConstant.RESULTS, resultMap);
		return map;
	}

	/**
	 * 统计投票结果
	 */
	@Override
	public Boolean statisticsVoteResult() {
		// TODO Auto-generated method stub
		Date date = new Date();
		Boolean result = false;
		Map<String, Object> voteResultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		voteResultMap = this.voteDao.selectVoteResultByDate(date);
		String yes = voteResultMap.get("yes").toString();
		String no = voteResultMap.get("no").toString();
		int yescount = 0;
		int nocount = 0;
		if(!yes.isEmpty()){
			yescount = Integer.valueOf(yes);
		}
		if(!no.isEmpty()){
			nocount = Integer.valueOf(no);
		}
		if(yescount > nocount){
			result = true;
		}else if(yescount == nocount){
			String weather = CommonUtil.getWeather();
			if(!weather.isEmpty()){
				if(weather.contains("晴")){
					result = true;
				}
			}
		}
		resultMap.put("voteyes", yescount);
		resultMap.put("voteno", nocount);
		resultMap.put("voteresult", result);
		resultMap.put("isexecute", result);
		resultMap.put("recorddatetime", date);
		this.liverecordDao.insertLiverecordSelective(resultMap);
		return result;
	}
}
