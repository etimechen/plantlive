package com.etimechen.component;

@SuppressWarnings("static-access")
public class Configurator {
	/**
	 * 执行时间
	 */
	private static Configurator config = null;
	private static PropertiesUtil propUtil = PropertiesUtil.getInstance();
	public static final String excuteTime = propUtil.getProperty("excutetime");
	
	public Configurator(){
		Configurator.getExcutetime();
	}
	
	public static Configurator getInstance(){
		config = new Configurator();
		return config;
	}
	
	private static String getExcutetime() {
		return excuteTime;
	}
}
