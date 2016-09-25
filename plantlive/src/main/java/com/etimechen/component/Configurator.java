package com.etimechen.component;

import com.etimechen.component.util.PropertiesUtil;

@SuppressWarnings("static-access")
public class Configurator {
	/**
	 * 执行时间
	 */
	private static Configurator config = null;
	private static PropertiesUtil propUtil = PropertiesUtil.getInstance();
	public static final String EXCUTE_TIME = propUtil.getProperty("excutetime");
	
	public Configurator(){
		Configurator.getExcuteTime();
	}
	
	public static Configurator getInstance(){
		config = new Configurator();
		return config;
	}

	public static String getExcuteTime() {
		return EXCUTE_TIME;
	}
	
}
