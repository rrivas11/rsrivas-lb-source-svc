package com.lifebank.source.lbsourcesvc.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenBuilder {
	JwtBuilder builder;
	Map<String, Object> claims;
	
	private TokenBuilder() {
		this.builder = Jwts.builder();
		this.claims = new HashMap<>();
	}
	
	public static TokenBuilder builder() {
		return new TokenBuilder();
	}
	
	public TokenBuilder setValidityTime(long time) {
		this.builder.setExpiration(new Date(System.currentTimeMillis() + time));
		
		return this;
	}
	
	public TokenBuilder setTokenIssuer(String issuer) {
		this.builder.setIssuer(issuer);
		
		return this;
	}
	
	public TokenBuilder addClaim(String key, Object value) {
		this.claims.put(key, value);
		
		return this;
	}
	
	public TokenBuilder addClaims(Map<String, Object> claims) {
		Map<String, Object> self = this.claims;
		
		claims.forEach((key, value) -> self.put(key, value));
		
		return this;
	}
	
	public TokenBuilder setSignature(SignatureAlgorithm signatureAlgorithm, String secret) {
		this.builder.signWith(signatureAlgorithm, secret);
		
		return this;
	}
	
	public String build(long milis) {
		this.builder.setClaims(this.claims);
		this.builder.setIssuedAt(new Date(System.currentTimeMillis()));

		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + milis;
		Date exp = new Date(expMillis);
		this.builder.setExpiration(exp);

		return this.builder.compact();
		
	}
}
