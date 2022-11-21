package com.adidas.backend.prioritysaleservice.controller;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/queue")
@Slf4j
public class QueueController {

    @Autowired
    private IQueueService queueService;
    
    @PostMapping("/start/{idSale}")
    public void queueStarted(@PathVariable String idSale) throws InterruptedException{
        try {
            log.info(String.format("Listener executed queueStarted({%s})", idSale));
            queueService.initQueue(idSale);
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }    

    @PutMapping("/pause/{idSale}")
    public void queuePause(@PathVariable String idSale) throws InterruptedException{
        try {
            log.info(String.format("Listener executed queuePause({%s})", idSale));
            queueService.pauseQueue(idSale);
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }  
    
    @PutMapping("/restart/{idSale}")
    public void queueRestart(String idSale) throws InterruptedException{
        try {
            log.info(String.format("Listener executed queueRestart({%s})", idSale));
            queueService.restartQueue(idSale);
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }  

    @GetMapping
    public ResponseEntity<List<QueueDto>> getQueueAll() throws InterruptedException{
        try{
            log.info(String.format("Listener executed getQueueAll"));
            var result=queueService.getQueue();
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }     
    
}
