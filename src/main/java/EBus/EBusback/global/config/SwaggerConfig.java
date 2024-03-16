package EBus.EBusback.global.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

// 스웨거 설정
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.info(new Info()  // 문서 설명
				.title("E-BUS API Documentation")
				.description(
					"2024 EWHA-THON [E-BUS] by Team Kim2leesongcha")
				.version("0.0.1"))
			.components(new Components()  // JWT 인증 설정
				.addSecuritySchemes("authorization",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.in(SecurityScheme.In.HEADER)
						.scheme("Bearer")
						.bearerFormat("JWT")))
			.security(Collections.singletonList(
				new SecurityRequirement().addList("authorization")));
	}
}
