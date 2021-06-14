package com.oracle.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createSwaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("sms-api-1.0").apiInfo(apiInfo()).select()
				.paths(p -> p.contains("/v1")).apis(RequestHandlerSelectors.basePackage("com.oracle.sms")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SMS Swagger API").description("Survey Management System APIs")
				.termsOfServiceUrl("").contact(new Contact("Nithin", "", "")).version("1.0").build();
	}

}