# Exception Handling

## Exception을 한 곳에서 처리
Spring Boot에서는 Controller에서 발생한 Exception을 AOP로 특정 클래스에서 처리하기 위한 `@ControllerAdvice` 어노테이션을 제공

또한, `@ControllerAdvice` 어노테이션을 가진 클래스 내의 메소드에 `@ExceptionHandler(Exception.class)`어노테이션을 정의하면 
어노테이션의 element로 지정된 Exception에 대한 처리를 할 수 있음

(`ExceptionAdvice` 클래스를 참조)

## Custom Exception 정의
애플리케이션 내에서 발생한 Exception에 대해 세부적으로 구분하고 처리하기 위해서 Custom Exception을 정의
(https://cheese10yun.github.io/spring-guide-exception/ 블로그 참조)

### 상속 관계
- RuntimeException
    - CustomException : 사용자 정의 Exception, 멤버 변수로 ErrorCode 클래스를 가짐
        - CustomEntityNotFoundException : 사용자가 요청한 리소스에 대해서 존재하지 않을 경우 (status : 404)
        - CustomInvalidValueException : 사용자가 유효하지 않은 입력값을 넘긴 경우 (status : 400)
    
> Exception을 더 세분화해서 정의할 수도 있지만, Exception을 추가해서 정의 하는 것보다 Error Code(Enum 타입)을 만들어 정의 하는 것이
> 클래스 추가에 대한 부담도 적고 유지보수가 수월하다 생각해서 세분화하지 않음

## Error Code 정의
발생한 에러의 경우마다 Error Code를 정의하여 기록하면 클라이언트에서 어떤 이유로 에러가 발생했는지 쉽게 알 수 있고,
유지보수 측면에서도 도움이 된다.

Error Code는 하나의 클래스에서 정의하며, `Enum` 타입으로 관리한다.


#### Error Code
- status : HTTP 상태 코드 (ex. 200, 404, 500 등)
- code : 사용자 정의 코드 (C001, C002 등)
- message :  에러 발생한 이유

## Error Response 정의
Error를 반환할 때는 통일된 객체의 형태로 반환해야 클라이언트가 혼란스럽지 않다.
따라서, `ErrorResponse` 클래스를 생성하여 에러 발생 시 응답에 대해 통일한다.

#### ErrorResponse

- Timestamp timestamp : 에러 발생 시간
- int status : HTTP 응답 코드
- String error : `HttpStatus`에 status 별로 정의된 reasonPhrase
- String message : 에러 발생 이유
- String path : 요청 URL
- String code : 사용자 정의 코드
- List<String> details : 에러에 대한 상세 정보

응답을 반환할때는 `ErrorResponse` 객체를 `ResponseEntity`의 `body`에 담아서 보낸다.

```java
	@ExceptionHandler(CustomEntityNotFoundException.class)
	protected ResponseEntity<?> entityNotFound(HttpServletRequest request, CustomEntityNotFoundException e) {
		ErrorResponse response = new ErrorResponse(e.getErrorCode(), request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}
```
