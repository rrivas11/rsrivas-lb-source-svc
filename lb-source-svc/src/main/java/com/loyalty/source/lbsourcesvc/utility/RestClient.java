package com.loyalty.source.lbsourcesvc.utility;

import org.slf4j.MDC;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RestClient {
	AsyncRestTemplate restTemplate;
	private Environment env;
	
	public RestClient(AsyncRestTemplate restTemplate, Environment env){
		this.restTemplate = restTemplate;
		this.env = env;
	}
		
	public <T, U> U call(String url, HttpMethod method, T requestObject, ParameterizedTypeReference<U> responseType,
			Object... uriParams) throws RestClientException, InterruptedException, ExecutionException, NumberFormatException, TimeoutException{
		HttpEntity<T> request; 
		PerformanceSampler performanceSampler = new PerformanceSampler();
		performanceSampler.startSample(url);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		ListenableFuture<ResponseEntity<U>> listenableFutureResponse;
		ResponseEntity<U> response;

		//headers.add("Authorization", "Basic");
		headers.add("Content-Type", "application/json");
		//Adding logger tracingToken to headers
		if(MDC.get(MDCHandler.constantes.trackingToken.name()) != null) {
			RestClientAppender appender = new RestClientAppender();
			appender.appendTracingTokenHeader(headers);
			appender.appendLmNumber(headers);
		}
		request = new HttpEntity<>(requestObject, headers);
		
		listenableFutureResponse = restTemplate.exchange(url, method, request, responseType, uriParams);
		
		response = listenableFutureResponse.get(Long.parseLong(env.getProperty("service.configuration.http.http-request-timeout")), TimeUnit.MILLISECONDS);

		performanceSampler.finishSample();
		return response.getBody();
	}
	public <T, U> U unauthorizedCall(String url, HttpMethod method, T requestObject, ParameterizedTypeReference<U> responseType,
						 Object... uriParams) throws RestClientException, InterruptedException, ExecutionException, NumberFormatException, TimeoutException{
		HttpEntity<T> request;
		PerformanceSampler performanceSampler = new PerformanceSampler();
		performanceSampler.startSample(url);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		ListenableFuture<ResponseEntity<U>> listenableFutureResponse;
		ResponseEntity<U> response;

		//headers.add("Authorization", "Basic");
		headers.add("Content-Type", "application/json");
		//Adding logger tracingToken to headers
		if(MDC.get("trackingToken") != null) {
			RestClientAppender appender = new RestClientAppender();
			appender.appendTracingTokenHeader(headers);
			appender.appendLmNumber(headers);
		}
		request = new HttpEntity<>(requestObject, headers);

		listenableFutureResponse = restTemplate.exchange(url, method, request, responseType, uriParams);

		response = listenableFutureResponse.get(Long.parseLong(env.getProperty("service.configuration.http.http-request-timeout")), TimeUnit.MILLISECONDS);

		performanceSampler.finishSample();
		return response.getBody();
	}
}
