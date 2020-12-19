package com.posts.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "Posts API",
		description = "API for posts management",
		contact = @Contact(name = "Adriano Souza Arruda", url = "https://www.linkedin.com/in/adrianog3/")
	)
)
public class OpenApiConfig {
}
