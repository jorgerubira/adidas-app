package com.adidas.backend.adiclubservice.controller;

import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IQueueInitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/queue")
@Slf4j
public class QueueController {
    
    @Autowired
    private IQueueInitService queueService;    
    
    @PostMapping("/{idSale}")
    public ResponseEntity<?> createQueue(@PathVariable String idSale){
        try {
            log.info(String.format("Listener executed createQueryMembers({%s})", idSale));
            queueService.initQueue(idSale);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }    
    
}
