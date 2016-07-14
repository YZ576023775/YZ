package com.yz.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yz.crawl.UpdateNewMusicCrawlStart;

public class MyTimer implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		UpdateNewMusicCrawlStart.main(null);
		
//		Email email = Email
//				.create()
//				.from("yz576023775@163.com")
//				.to("576023775@qq.com")
//				.subject("Crawl")
//				.addText("Crawl Complete！");
////		SimpleAuthenticator sa = new SimpleAuthenticator("171733817@qq.com",
////				"YZorange1");
////		SmtpSslServer server = new SmtpSslServer("smtp.qq.com");
//		SmtpSslServer smtpServer = SmtpSslServer
//				.create("smtp.163.com")
//				.authenticateWith("yz576023775@163.com", "yzORANGE1");
//		SendMailSession session = smtpServer.createSession();
//		session.open();
//		session.sendMail(email);
//		session.close();
//		
//		System.out.println("发送至QQ邮箱成功!...");
	}
	
	
	}

