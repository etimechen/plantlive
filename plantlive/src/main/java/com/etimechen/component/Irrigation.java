package com.etimechen.component;

import org.apache.log4j.Logger;

import com.etimechen.component.constant.ConfigConstant;

public class Irrigation {
	
	private static final Logger logger = Logger.getLogger(Irrigation.class);
	
	/**
	 * 浇花
	 */
	public static void start(){
		logger.info("开始浇花");
	}
	
	/**
	 * 停止浇花
	 */
	public static void stop(){
		logger.info("停止浇花");
	}
	
	/**
	 * 执行一个浇花动作
	 */
	public static void dooneperiod(){
		start();
		try {
			Thread.sleep(ConfigConstant.IRRIGATION_MILLIS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stop();
	}

}
