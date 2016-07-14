package com.yz.timer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class TimerListener implements JobListener{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		System.out.println("getName");
		return "testListenerName";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		
		System.out.println("jobToBeExecuted");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println("jobExecutionVetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		// TODO Auto-generated method stub
		System.out.println("jobWasExecuted");
	}

}
