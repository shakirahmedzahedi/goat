package com.saz.se.goat.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API Title").version("1.0").description("Your API Description"))
                .components(new Components()
                        // Define JWT Bearer Token Authentication
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))

                        // Define custom email header parameter
                        .addSecuritySchemes("email", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .scheme("basic")
                        .in(SecurityScheme.In.HEADER)
                        .name("email"))
                )

                // Add both JWT and email as security requirements
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")
                        .addList("email"));
    }
}



