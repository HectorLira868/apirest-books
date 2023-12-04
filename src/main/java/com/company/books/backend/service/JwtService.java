package com.company.books.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
	  private static final String JWT_SECRET_KEY = "bWkgY2xhdmUgZXMgbXV5IHNlZ3VyYSAxMjM0NTY3ODkgQUJDYWJj";

	  public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 1; // 1 hora

	  public String extractUsername(String token) {
	    return extractClaim(token, Claims::getSubject);
	  }

	  public Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	  }

	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    return claimsResolver.apply(extractAllClaims(token));
	  }

	  private Claims extractAllClaims(String token) {
	    return Jwts.parserBuilder().setSigningKey(generateKeys()).build().parseClaimsJws(token).getBody();
	  }

	  private Boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	  }

	  public String generateToken(UserDetails userDetails) {
	    Map<String, Object> claims = new HashMap<>();
	    var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
	    claims.put("rol", rol);
	    return createToken(claims, userDetails.getUsername());
	  }

	  private String createToken(Map<String, Object> claims, String subject) {

		  Date IssuedAt = new Date(System.currentTimeMillis());
		  Date Expiration = new Date(IssuedAt.getTime() + JWT_TOKEN_VALIDITY);
		  
	    return Jwts
	        .builder()
	        .setClaims(claims)
	        .setSubject(subject)
	        .setIssuedAt(IssuedAt)
	        .setExpiration(Expiration)
	        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
	        .signWith(generateKeys(), SignatureAlgorithm.HS256)
	        .compact();
	  }

	  private Key generateKeys() {
		  byte[] secretsBytes =  Decoders.BASE64.decode(JWT_SECRET_KEY);
		  
		return Keys.hmacShaKeyFor(secretsBytes);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }

}

