package com.etimechen.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiesUtil {

	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

	private static PropertiesUtil propertiesUtil = null;
	
	private static Properties p = null;
	

	public PropertiesUtil() {
		logger.info("读取配置文件");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/conf/config.properties");
		try {
			p = new Properties();
			p.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("读取配置文件失败");
			e.printStackTrace();
		}
		
	}
	
	public static PropertiesUtil getInstance(){
		propertiesUtil = new PropertiesUtil();
		return propertiesUtil;
	}
	
	/**
	 * 获取属性值
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key){
		return p.getProperty(key);
	}
}
