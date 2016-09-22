package com.etimechen.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.etimechen.service.IVoteService;

public class VoteTest {
	
	private IVoteService voteService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
				,"classpath:conf/spring-mybatis.xml"});
		voteService = (IVoteService) context.getBean("voteService");
	}
	
	@Test
	public void vote(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("voteyesorno", 1);
		paramMap.put("voteip", Long.valueOf("3232235779"));
		paramMap.put("votedate", "2016-09-17");
		System.out.println(voteService.insertVote(paramMap));
		System.out.println(paramMap);
	}
	
	@Test
	public void test(){
		
			//System.out.println(Hex.encodeHex(null));

	}
}
