package com.etimechen.component;

import java.io.IOException;

import com.etimechen.component.constant.ConfigConstant;

public class Irrigation {
	
//	private static final Logger logger = Logger.getLogger(Irrigation.class);
	private static final String[] START = {"sudo","/home/pi/project/RPi_Relay_Board/shell/Relay.sh","CH3","ON"};
	private static final String[] STOP = {"sudo","/home/pi/project/RPi_Relay_Board/shell/Relay.sh","CH3","OFF"};
	
	/**
	 * 浇花
	 */
	public static void start(){
//		logger.info("开始浇花");
		Process pro;
		try {
			pro = Runtime.getRuntime().exec(START);
	        pro.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	/**
	 * 停止浇花
	 */
	public static void stop(){
//		logger.info("停止浇花");
		Process pro;
		try {
			pro = Runtime.getRuntime().exec(STOP);
	        pro.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
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
