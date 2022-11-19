package com.adidas.backend.prioritysaleservice.listener;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueListener {
    @Autowired
    private IJsonConverter json;

    @Autowired
    private IQueueService queueService;
    
    @KafkaListener(topics = MQTopics.QUEUE_STARTED)
    public void queueStarted(String pIdSale) throws InterruptedException{
        try {
            String idSale=json.deserialize(pIdSale, String.class);
            log.info(String.format("Listener executed queueStarted({%s})", idSale));
            queueService.initQueue(idSale);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }    

    @KafkaListener(topics = MQTopics.QUEUE_PAUSE)
    public void queuePause(String pIdSale) throws InterruptedException{
        try {
            String idSale=json.deserialize(pIdSale, String.class);
            log.info(String.format("Listener executed queuePause({%s})", idSale));
            queueService.pauseQueue(idSale);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }  
    
    @KafkaListener(topics = MQTopics.QUEUE_RESTART)
    public void queueRestart(String pIdSale) throws InterruptedException{
        try {
            String idSale=json.deserialize(pIdSale, String.class);
            log.info(String.format("Listener executed queueRestart({%s})", idSale));
            queueService.restartQueue(idSale);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }  

    @KafkaListener(topics = MQTopics.QUEUE_GET_ALL) 
    @SendTo
    public String getQueueAll() throws InterruptedException{
        try{
            try{
                log.info(String.format("Listener executed getQueueAll"));
                var result=queueService.getQueue();
                var r=json.serialize(result);
                return r;
            }catch(IdNotFoundException e){
                return json.serialize(MemberBasicInfoEvent.builder().status(403).message("Id not found").build());
            }
        }catch(JsonProcessingException | ExternalException  e){
            log.error("Error " + e.getMessage());
            return "";
        }
    }     
    
}
