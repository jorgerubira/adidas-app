package com.adidas.backend.adiclubservice.listener;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IQueueInitService;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueListener {

    @Autowired
    private IJsonConverter json;

    @Autowired
    private IQueueInitService queueService;
    
    @KafkaListener(topics = MQTopics.QUEUE_CREATE)
    public void createQueryMembers(String pIdSale) throws InterruptedException{
        try {
            String idSale=json.deserialize(pIdSale, String.class);
            log.info(String.format("Listener executed createQueryMembers({%s})", idSale));
            queueService.initQueue(idSale);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
    
}
