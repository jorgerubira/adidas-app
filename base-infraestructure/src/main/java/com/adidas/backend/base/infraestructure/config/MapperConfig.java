package com.adidas.backend.base.infraestructure.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfig {
    
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
    

}