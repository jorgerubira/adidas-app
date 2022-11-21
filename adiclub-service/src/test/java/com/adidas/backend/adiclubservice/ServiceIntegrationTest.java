package com.adidas.backend.adiclubservice;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties
        = {
            "app.emails-by-minute=6",
            "spring.jpa.hibernate.ddl-auto=update",
            "spring.datasource.url=jdbc:mysql://localhost:3306/adidas?createDatabaseIfNotExist=true",
            "spring.datasource.username=root",
            "spring.datasource.password=1111",
            "spring.datasource.driver-class-name=com.mysql.jdbc.Driver",
            "spring.datasource.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect",
            "spring.datasource.show-sql=true",
            "spring.kafka.bootstrap-servers=localhost:9092",
            "spring.kafka.consumer.group-id=group-adidas",
            "spring.kafka.consumer.auto-offset-reset=latest",
            "spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
            "spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
            "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
            "spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer",
            "spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER"
        })
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
