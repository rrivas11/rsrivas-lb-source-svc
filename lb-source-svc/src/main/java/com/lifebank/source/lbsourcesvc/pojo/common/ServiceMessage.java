package com.lifebank.source.lbsourcesvc.pojo.common;

public class ServiceMessage<H, B> {
	private H header;
	private B body;

	public ServiceMessage(){
		
	}
	
	public ServiceMessage(H header, B body){
		this.body = body;
		this.header = header;
	}

	public H getHeader() {
		return header;
	}

	public void setHeader(H header) {
		this.header = header;
	}

	public B getBody() {
		return body;
	}

	public void setBody(B body) {
		this.body = body;
	}
}
