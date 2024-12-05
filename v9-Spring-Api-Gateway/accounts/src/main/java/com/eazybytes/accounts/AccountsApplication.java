package com.eazybytes.accounts;

import com.eazybytes.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//OpenApi Documentaion is used to generate API documentation
// and giving information about API


@SpringBootApplication
@EnableConfigurationProperties(value={AccountContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@ComponentScan(basePackages = {"com.eazybytes.accounts"})
@OpenAPIDefinition(info=@Info
		(title = "Accounts Microservices API documentation",
        description =  "EazyBank Accounts Microservice Rest API Documentation",
				version = "v1",
				contact = @Contact
						(name = "Tushar", email = "azyBytes@gmail.com",url = "http://www.eazyBytes.com"),
				license =@License
						(name="Apache 2.0",url="http://www.eazyBytes.com/license")
		),
		externalDocs=@ExternalDocumentation(
				description = "EazyBank Accounts Microservice Rest APIDocumentation"
				,url = "http://www.eazyBytes.com/swagger-ui.html")
		)

@EnableFeignClients
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
