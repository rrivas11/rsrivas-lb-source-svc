package com.lifebank.source.lbsourcesvc.utility;


import org.slf4j.MDC;
import org.springframework.util.MultiValueMap;

public class RestClientAppender {
	public RestClientAppender() {
		
	}
	public void appendTracingTokenHeader(MultiValueMap<String, String> headers) {
		String trackingToken = MDC.get("trackingToken");
		if(trackingToken != null)
		{
			headers.add("trackingToken", trackingToken);
		}
	}
	public void appendLmNumber(MultiValueMap<String, String> headers) {
		String requestId = MDC.get(MDCHandler.constantes.LmNumber.name());
		if(requestId != null) {
			headers.add(MDCHandler.constantes.LmNumber.name(), requestId);
		}
	}

}