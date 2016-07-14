package com.yz.timer;

import java.io.InputStream;
import java.text.ParseException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger.TriggerState;
import org.quartz.core.ListenerManagerImpl;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class Schedue {
	public static void main(String[] args) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("crawl.cfg.xml");
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(is);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = document.getRootElement();
		String element = root.elementTextTrim("cron");
		System.out.println(element);
		StdSchedulerFactory ssf = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = ssf.getScheduler();
			JobDetail jobDetail = new JobDetailImpl("crawl1", "crawl_group",
					MyTimer.class);
			CronTrigger cronTrigger = new CronTriggerImpl("trigger",
					"trigger_group", element);
			scheduler.scheduleJob(jobDetail, cronTrigger);
			
//			JobListener jobListener = new TimerListener();
//			ListenerManager listenerManager = new ListenerManagerImpl();
//			listenerManager.addJobListener(jobListener);
			scheduler.start();
			
//			TriggerState  triggerState= scheduler.getTriggerState(cronTrigger.getKey());
//			System.out.println(triggerState.toString());
		} catch (SchedulerException | ParseException e) {
			e.printStackTrace();
		}
	}

}
