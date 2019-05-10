package com.loyalty.source.lbsourcesvc.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PerformanceSampler {
	private static Logger log;
	private String endpointName;
	private LocalDateTime beginningTime;
	private LocalDateTime endTime;
	private LocalDateTime tempTime;
	
	public PerformanceSampler() {
	}
	public void startSample(String endpoint)
	{
		this.log = LoggerFactory.getLogger(getClass());
		this.beginningTime = LocalDateTime.now();
		this.endpointName = endpoint;
		if(endpoint != null)
		{
			log.info("endpoint: " + endpoint);
		}
	}
	public void finishSample() {
		this.endTime = LocalDateTime.now();
		this.tempTime = LocalDateTime.from(beginningTime);
		long milliSeconds = this.tempTime.until(endTime, ChronoUnit.MILLIS);
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String className = stackTrace[2].getClassName();
		String method = stackTrace[2].getMethodName();
		if(className.contains("RestClient"))
		{
			className = stackTrace[4].getClassName();
			method = stackTrace[4].getMethodName();
		}
		if(endpointName != null)
		{
			log.info("Performance of " + className + "." + method + "() is " + milliSeconds + " milliseconds, endpoint: " + endpointName);
		}
		else {
			log.info("Performance of " + className + "." + method + "() is " + milliSeconds + " milliseconds");
		}
	}
}
