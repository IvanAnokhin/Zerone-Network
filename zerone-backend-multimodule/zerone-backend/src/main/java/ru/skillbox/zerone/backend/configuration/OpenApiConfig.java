package ru.skillbox.zerone.backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI().info(new Info().title("Application API")
            .version("1.0")
            .description("Zerone social network API")
            .license(new License().name("Apache 2.0")
                .url("http://springdoc.org"))
            .contact(new Contact().name("username")
                .email("test@gmail.com")))
        .servers(List.of(new Server().url("http://zerone-network.ru")
                .description("Dev service")));
  }
}
