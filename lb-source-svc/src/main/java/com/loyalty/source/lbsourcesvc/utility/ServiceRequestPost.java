package com.loyalty.source.lbsourcesvc.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ServiceRequestPost<T,U> {

	private RestClient restClient;
	private Logger log;
	
	@Autowired
	public ServiceRequestPost(RestClient restClient) {
		this.restClient = restClient;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	public T RequestPost(String endpointUrl, ParameterizedTypeReference<T> responseType, U request) {
		T responsePeticion;
		try {
			responsePeticion = restClient.call(
				endpointUrl,
				HttpMethod.POST,
				request,
				responseType);
		}catch(RestClientException | NumberFormatException | InterruptedException | ExecutionException | TimeoutException e){
			e.printStackTrace();
			log.error(" Hubo un error RequestPost {}, en la línea{}, del método {}",
					e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
			return null;
		}		
		//return null;
		return responsePeticion;
	}
	
}
