package com.gokoy.delivery.global.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	// application.yaml에 설정된 JWT Token 생성 및 유효성 검증에 사용되는 SECRET KEY 값
	@Value("spring.jwt.secret")
	private String SECRET_KEY;

	// 1000 milliseconds * 60 seconds * 60 minutes
	private long tokenValidMillisecond = 1000L * 60 * 60;

	private final MemberServiceForAuth memberServiceForAuth;

	@PostConstruct
	protected void init() {
		SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
	}

	public String createToken(String userPk, List<String> roles) {
		// Claim에 데이터 저장
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + tokenValidMillisecond))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY) // JWT 암호화 알고리즘, SECRET KEY 세팅
			.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = memberServiceForAuth.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUserPk(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
	// X prefix가 붙은 Header는 사용자가 직접 정의했다는 의미
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
	}

	// Jwt 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}
