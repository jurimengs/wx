package com.org.cron;

import com.org.utils.BeanUtils;

public class CronServiceFactory {
	//private Log log = LogFactory.getLog(CronServiceFactory.class);
	
	private static CronServiceFactory factory;
	
	private CronServiceFactory(){
		
	}
	
	public static CronServiceFactory getInstance(){
		if(factory == null)
			factory = new CronServiceFactory();
		return factory;
	}
	
	public CronService  createCronService(String beanName){
		CronService cs = null;
		cs = (CronService)BeanUtils.getBean(beanName);
		return cs;
	}


	
	
}
