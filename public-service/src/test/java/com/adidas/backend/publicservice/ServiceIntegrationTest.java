package com.adidas.backend.publicservice;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServiceIntegrationTest {
	
        @LocalServerPort
	int port;

	@Autowired
	private RestTemplateBuilder builder;

	@Test
	void testOwnerDetails() {
            RestTemplate template = builder.rootUri("http://localhost:" + port).build();
            ResponseEntity<String> result = template.exchange(RequestEntity.get("/check_ok").build(), String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

        
}
