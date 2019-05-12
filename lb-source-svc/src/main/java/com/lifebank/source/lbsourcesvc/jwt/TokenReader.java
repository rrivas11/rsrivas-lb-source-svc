package com.lifebank.source.lbsourcesvc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.HashMap;
import java.util.Map;

public class TokenReader {
	public static Map<String, String> readClaims(String token, String secret){
		Map<String, String> data = new HashMap<>();
		
		Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
		
		claims.forEach((key, value) -> data.put(key, value.toString()));
		
		return data;
	}
}
