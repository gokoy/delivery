package com.gokoy.delivery.global.config.security;

import com.gokoy.delivery.domain.ceo.application.CeoServiceForAuth;
import com.gokoy.delivery.domain.consumer.application.ConsumerServiceForAuth;
import com.gokoy.delivery.global.common.model.Role;
import com.gokoy.delivery.global.error.exception.CustomInvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    public static final String AUTH_HEADER = "X-AUTH-TOKEN";

    @Value("spring.jwt.secret") // application.yaml에 설정된 JWT Token 생성 및 유효성 검증에 사용되는 SECRET KEY 값
    private String SECRET_KEY;

    private long tokenValidMillisecond = 1000L * 60 * 60; // 1000 milliseconds * 60 seconds * 60 minutes

    private final CeoServiceForAuth ceoServiceForAuth;

    private final ConsumerServiceForAuth consumerServiceForAuth;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email); // claim의 subject로 email 저장
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // JWT 암호화 알고리즘, SECRET KEY 세팅
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = (String) claims.get("role");
        String email = claims.getSubject();
        UserDetails userDetails = null;

        switch (Role.valueOf(role)) {
            case CEO:
                userDetails = ceoServiceForAuth.loadUserByUsername(email);
                break;
            case CONSUMER:
                userDetails = consumerServiceForAuth.loadUserByUsername(email);
                break;
        }

        if (userDetails == null) {
            throw new CustomInvalidJwtException();
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /*
     * Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
     * X prefix가 붙은 Header는 사용자가 직접 정의했다는 의미
     */
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(AUTH_HEADER);
    }

    /*
     * Jwt 토큰의 유효성 + 만료일자 확인
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new CustomInvalidJwtException();
        }
    }

}
