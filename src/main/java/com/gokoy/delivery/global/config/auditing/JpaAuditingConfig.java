package com.gokoy.delivery.global.config.auditing;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // audit 기능 활성화
public class JpaAuditingConfig {
}
