package com.lifebank.source.lbsourcesvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ServiceConfiguration {
	private Environment env;
	
	@Autowired
	public ServiceConfiguration(Environment env){
		this.env = env;
	}

}
