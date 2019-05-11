package com.loyalty.source.lbsourcesvc.configuration;

import com.loyalty.source.lbsourcesvc.utility.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class ServiceConfiguration {
	private Environment env;
	
	@Autowired
	public ServiceConfiguration(Environment env){
		this.env = env;
	}
	
	@Bean
	public RestClient restClient(){
	    return new RestClient(new AsyncRestTemplate(), env);
	}
}
