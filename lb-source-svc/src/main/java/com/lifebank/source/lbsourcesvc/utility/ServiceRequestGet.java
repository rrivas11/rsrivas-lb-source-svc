package com.lifebank.source.lbsourcesvc.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ServiceRequestGet<T> {
	private RestClient restClient;
	private Logger log;
	@Autowired
	public ServiceRequestGet(RestClient restClient) {
		this.restClient = restClient;
		this.log = LoggerFactory.getLogger(getClass());
	}

	public T requestGet(String endpointUrl, ParameterizedTypeReference<T> responseType) {
		T responsePeticion;
		try {
			responsePeticion = restClient.call(
					endpointUrl,
					HttpMethod.GET,
					null,
					responseType);
		}catch(RestClientException | NumberFormatException | InterruptedException | ExecutionException | TimeoutException e){
			//MDCHandler.setSenderRequest(endpointUrl);
			//clmLogger.logGeneralError(e, "Exception requesting get service" + endpointUrl);
			e.printStackTrace();
			return null;
		}
		return responsePeticion;
	}

}
