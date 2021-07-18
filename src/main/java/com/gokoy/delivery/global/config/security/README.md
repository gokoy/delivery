## Spring Security

Spring 기반 어플리케이션의 인증과 인가 등 보안을 담당

Spring Security와 관련된 설정을 재정의해서 사용할 수 있도록 SecurityConfig 클래스를 생성

Spring Security에서 기본적으로 제공하는 CSRF 보호 사용하지 않음

Request를 보낸 사용자의 Role 별로 접근할 수 있는 URL을 별도로 정의

Password 암호화에 사용될 PasswordEncoder 객체 DI

## JWT 기반 인증
기존에 Spring Security가 기본적으로 제공하는 `http basic 인증` 방식, `Session`을 사용하지 않고 `JWT` 기반의 인증을 토대로 사용자 인증, 인가 여부를 확인

JWT 토큰 생성, 유효성 검증 등의 메소드를 가진 `JwtTokenProvider` 클래스를 생성 (`io.jsonwebtoken` 패키지 사용)

모든 호출에 대하여 JWT 토큰의 유효성을 검증하는 사용자 정의 Filter인 JwtAuthenticationFilter를 생성

JwtAuthenticationFilter는 UsernamePasswordAuthenticationFilter 이전에 삽입하여 HTTP Request Header의 X-AUTH-TOKEN으로 들어온 JWT 토큰 확인하여 인증 확인

---

## Sign-up Request Flow

1. 회원 정보 입력
2. email 중복 확인
   - 중복 시 Exception 발생
3. 회원 정보 저장
4. Response 반환(200)

## Sign-in Request Flow

1. 회원 정보 입력
2. 유효성 검사
   - 유효하지 않은 값 존재 시, Exception 발생
3. 회원정보 일치 여부 확인
   - email & password 불일치 시, Exception 발생
4. JWT 토큰 생성
5. Response에 JWT 담아 반환(200)

## Normal Requset Flow

1. 사용자 request 발생
2. Spring Security Filter Chain으로 request 전달
3. Filter Chain 통과 중 `JwtAuthenticationFilter`(사용자 정의 Filter)에서 JWT 토큰 유효성 검사 진행
   - 유효한 토큰일 경우, `UsernamePasswordAuthenticationToken`(`Authentication` 인터페이스 구현체) 객체  생성하여 `SecurityContextHolder`를 이용하여 `SecurityContext`에 저장
4. Spring Security Filter Chain의 마지막 Filter 인 `FilterSecurityInterceptor`해당 요청에 대한 인증, 인가가 유효한지 확인
5. 인증되고 권한이 있는 사용자에 대해 리소스 접근 허용
6. 접근한 API에 대한 로직 실행
7. Response 반환
