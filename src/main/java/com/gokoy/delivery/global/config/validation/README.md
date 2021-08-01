# 유효성 검사
애플리케이션 로직에서 예기치 않은 오류 발생을 예방하기 위해 다양한 계층에서 데이터 유효성 검사를
진행합니다. 하지만, 모든 계층에서 데이터 유효성을 검사하는 것은 낭비이므로 Domain 또는 API에서 전달되는
객체(DTO)에서 유효성 검사를 진행합니다.

### 사용되는 라이브러리
- javax.validation.constraints
  https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html
  
- hibernate.validator.constraints
  https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/
  
## Enum 유효성 검사
위 라이브러리에서 제공되는 어노테이션으로 검증할 수 없는 데이터의 경우, 직접 유효성 검사 어노테이션을
작성하여 사용할 수 있다.

입력되는 데이터가 특정 `Enum`의 값을 가져야 할 때 아래와 같이 정의하여 사용한다.

> **참고한 사이트**
> - https://velog.io/@hellozin/Annotation%EC%9C%BC%EB%A1%9C-Enum-%EA%B2%80%EC%A6%9D%ED%95%98%EA%B8%B0
> - https://jongmin92.github.io/2019/11/21/Spring/bean-validation-2/

```java
// 해당 annotation이 실행 할 ConstraintValidator 구현체를 `EnumValidator`로 지정합니다.
@Constraint(validatedBy = {EnumValidator.class})
// 해당 annotation은 메소드, 필드, 파라미터에 적용 할 수 있습니다.
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
// annotation을 Runtime까지 유지합니다.
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {
	
	// ValidationMessages.properties에서 특정 property key를 가리키는 메시지 (제약 조건 위반시 메시지로 사용)
	String message() default "Invalid value. This is not permitted.";

	// 유효성 검사가 어떤 상황에서 실행되는지 정의할 수 있는 매개 변수 그룹
	Class<?>[] groups() default {};

	// 유효성 검사에 전달할 payload를 정의할 수 있는 매개 변수
	Class<? extends Payload>[] payload() default {};

	// 입력 값에 대한 유효성 검사를 할 Enum 클래스
	Class<? extends java.lang.Enum<?>> enumClass();

	// 대소문자 구별 여부 flag
	boolean ignoreCase() default false;
}
```

`@Constraint`은 validatedBy 엘리먼트에 validator 클래스를 지정하면 해당하는 유효성 검사를 실행합니다.

`@Constraint`의 코드를 보면 엘리먼트가 아래와같이 `ConstraintValidator` 인터페이스를 상속한 클래스를 받도록 정의되어 있습니다.
```java
Class<? extends ConstraintValidator<?, ?>>[] validatedBy();
```

따라서, Validator 클래스를 정의할 때는 `ConstraintValidator` 인터페이스를 구현하여 만들어 줍니다.

```java
public class EnumValidator implements ConstraintValidator<Enum, String> {
	private Enum annotation;

	@Override
	public void initialize(Enum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean result = false;
		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (value.equals(enumValue.toString())
					|| (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}
```
