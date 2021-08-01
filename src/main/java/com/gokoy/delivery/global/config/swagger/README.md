# build.gradle에 dependencies 추가
```java
dependencies {
    implementation "io.springfox:springfox-boot-starter:3.0.0"
    implementation "io.springfox:springfox-swagger-ui:3.0.0"
}
```

# SwaggerConfig 클래스 생성

```java
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
	}
}
```

# Spring Security 설정에서 Swagger 관련 URL 접근 가능하도록 설정
```java
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        ...
            .mvcMatchers("/v2/**",
            "/configuration/**",
            "/swagger*/**",
            "/webjars/**",
            "/swagger-resources/**").permitAll()
        ...
    }      
}
```

# Application 기동 후, Swagger 접속

http://localhost:8080/swagger-ui/index.html

# Swagger에서 Request Header 전역 설정
```java
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                    ...
                    .securityContexts(Collections.singletonList(securityContext()))
                    .securitySchemes(Collections.singletonList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", JwtTokenProvider.AUTH_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext
			.builder()
			.securityReferences(defaultAuth())
			.operationSelector(operationContext -> true)
			.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}
```
