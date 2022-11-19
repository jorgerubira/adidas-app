package com.adidas.backend.publicservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;



@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AdiMembers Suscriptions",
                version = "v1",
                description = "This API allow to manage queues and sending emails to members when a sale is released",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(url = "http://www.adidas.com", name = "Adidas", email = "contact@adidas.es")
        )
)
public class SwaggerConfig {
    
}
