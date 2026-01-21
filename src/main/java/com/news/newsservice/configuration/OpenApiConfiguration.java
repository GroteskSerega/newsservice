package com.news.newsservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Sergey Nochvay");
        contact.setEmail("S@gmail.com");
        contact.setUrl("http://github.com");

        License mitLicense = new License().name("GNU AGPLv3")
                .url("https://chooselicense.com/license/agpl-3.0/");

        Info info = new Info()
                .title("News API")
                .version("1.0")
                .contact(contact)
                .description("API for news")
                .termsOfService("http://some.terms.url")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localhostServer));
    }
}
