package com.baio.money_minder.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;

@OpenAPIDefinition(
        info = @Info(
                title = "Money Minder API",
                description = "Welcome the the money minder API! Here you can see new changes" +
                        " to the API That will eventually be used for features in the app",
                contact = @Contact(
                        name = "Andres Antonio Castro Beltran",
                        email = "andres435b@gmail.com",
                        url = "http://www.linkedin.com/in/andres-castro435"
                ),
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Production ENV",
                        url = "https://money-minder-spring-boot-723598043884.northamerica-south1.run.app"
                ),
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {

}