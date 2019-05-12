package com.lifebank.source.lbsourcesvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class LbSourceSvcApplication {

	private static Logger log;

	public LbSourceSvcApplication() {
		this.log = LoggerFactory.getLogger(getClass());

	}

	public static void main(String[] args) {

		try {
		System.setProperty("ip", InetAddress.getLocalHost().getHostAddress());
		SpringApplication.run(LbSourceSvcApplication.class, args);
		}catch(UnknownHostException e) {
			log.error(" Hubo un error al consultar Application {}, en la línea{}, del método {}", e ,e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());

		}
	}

}
