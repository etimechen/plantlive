package com.etimechen.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.etimechen.component.Configurator;

@Component
public class Task {
	
	private static final Logger logger = Logger.getLogger(Task.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public void irrigation(){		
		logger.info("灌溉任务执行开始");
		Date date = new Date();		
		String nowHourMinutes = sdf.format(date);
		if(nowHourMinutes.equals(Configurator.EXCUTE_TIME)){
			logger.info("时间到了,开始一个浇水动作");
		}else{
			logger.info(nowHourMinutes);
			logger.info(Configurator.EXCUTE_TIME);
			logger.info("时间还没到");
		}
		logger.info("灌溉任务执行结束");
	}
}
