package com.etimechen.component;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = Logger.getLogger(Initializer.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		logger.debug("所有bean加载完成,初始化config.properties全局变量");
		Configurator.getInstance();
	}
}
