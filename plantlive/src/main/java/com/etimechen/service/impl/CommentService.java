package com.etimechen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.etimechen.component.constant.ConfigConstant;
import com.etimechen.component.constant.MessageConstant;
import com.etimechen.dao.ICommentDao;
import com.etimechen.service.ICommentService;

@Service
public class CommentService implements ICommentService {

	@Resource
	private ICommentDao commentDao;
	
	@Override
	public Object selectCommentByLimit(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub	
		Map<String, Object> map = new HashMap<String, Object>();
		Object start = paramMap.get("start");
		Object limit = paramMap.get("limit");
		Map<String, Integer> limitMap = new HashMap<String, Integer>();
		if(start!=null&&limit!=null){			
			limitMap.put("start", Integer.valueOf(start.toString()));
			limitMap.put("limit", Integer.valueOf(limit.toString()));
		}
		List<Object> list = (List<Object>) this.commentDao.selectCommentByLimit(limitMap);
		Integer resultsCount = this.commentDao.selectCommentCountByLimit();
		map.put(MessageConstant.SUCCESS, true);
		map.put(MessageConstant.RESULTS, list);
		map.put(MessageConstant.RESULTS_COUNT, resultsCount);
		return map;
	}

	@Override
	public Object insertComment(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if(paramMap.get("commentcontent").toString().length() > 500){			
			map.put(MessageConstant.SUCCESS, false);
			map.put(MessageConstant.MSG, "limit 500");
			return map;
		}else{
			this.commentDao.insertCommentSelective(paramMap);
			Map<String, Integer> limitMap = new HashMap<String, Integer>();
			limitMap.put("start", ConfigConstant.PAGE_START);
			limitMap.put("limit", ConfigConstant.PAGE_LIMIT);
			List<Object> list = (List<Object>) this.commentDao.selectCommentByLimit(limitMap);
			map.put(MessageConstant.SUCCESS, true);
			map.put(MessageConstant.RESULTS, list);
		}
		return map;
	}

}
