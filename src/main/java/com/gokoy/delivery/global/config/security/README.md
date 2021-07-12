# Spring Security 설정


#### JWT를 이용한 토큰 기반 인증 사용

Session 사용하지 않음

JWT 토큰의 유효성을 확인하기 위해 사용자 필터 정의 (JwtAuthenticationFilter)

JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 삽입하여 request의 JWT 토큰 확인하여 인증 확인

#### Spring Security에서 제공하는 HTTP Basic 사용하지 않음

로그인 API 직접 구현

#### Spring Security에서 제공하는 CSRF 보호 사용하지 않음

---

#### Sign-up Request Flow

1. 회원 정보 입력
2. email 중복 확인
   1. 중복 시 Exception 발생
3. 회원 정보 저장
4. Response 반환(200)

#### Sign-in Request Flow

1. 회원 정보 입력
2. 유효성 검사
   1. 유효하지 않은 값 존재시 Exception 발생
3. 회원정보 탐색
4. email & password 일치 여부 확인
   1. 불일치 시, Exception 발생
5. JWT 토큰 생성
6. Response에 JWT 담아 반환(200)

#### Normal Requset Flow

1. 사용자 request 발생
2. Spring Filter로 request 전달
3. Spring Security Filter Chain으로 request 전달
4. Filter Chain 통과 중 `JwtAuthenticationFilter`(사용자 정의 Filter)에서 JWT 토큰 유효성 검사 진행
   1. 유효한 토큰일 경우, `UsernamePasswordAuthenticationToken`(`Authentication` 인터페이스 구현체) 객체  생성하여 `SecurityContextHolder`를 이용하여 `SecurityContext`에 저장
5. 
