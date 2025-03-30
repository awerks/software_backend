package com.seproject.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info();
        info.setTitle("Teamspace Project Implemented API");
        info.setDescription(
                "API documentation for the Teamspace Project, which includes user authentication, project management, teamspaces, tasks, chat, permissions, and deadline management.");
        License license = new License();
        license.setName("MIT");
        license.setUrl("https://github.com/awerks/software_backend/blob/main/LICENSE");
        info.setLicense(license);
        io.swagger.v3.oas.models.info.Contact contact = new io.swagger.v3.oas.models.info.Contact();
        contact.setName("awerks");
        contact.setUrl("https://github.com/awerks");
        info.setContact(contact);

        return new OpenAPI()
                .info(info)
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080"),
                        new io.swagger.v3.oas.models.servers.Server().url("https://se-backend.up.railway.app/")));
    }
}