package com.adidas.backend.publicservice.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InfoListener {
    
    @KafkaListener(topics = "createSale")
    public void getAllSales(String s){
        System.out.println("Llegaaaa");
        log.info("Lleeeeega info: " + s);
    }
}
