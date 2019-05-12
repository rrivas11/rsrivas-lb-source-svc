package com.lifebank.source.lbsourcesvc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenMethods {
	private Environment env;
	private Logger log;
	
	@Autowired
	public TokenMethods(Environment env) {
		this.env= env;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	public String createToken(Map<String, Object> claims) {
		return TokenBuilder.builder()
			//.setValidityTime()
			.setSignature(SignatureAlgorithm.HS512, env.getProperty("appProperties.jwt.secret"))
			.addClaims(claims)
			.build(Long.valueOf(env.getProperty("appProperties.jwt.expiration-time")));
	}
	

	
	public Map<String, Object> readClaims(String token, String secret){
		Map<String, Object> data = new HashMap<String, Object>();
		
		Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
		
		claims.forEach((key, value) -> data.put(key, value));
		
		return data;
	}
	

}
