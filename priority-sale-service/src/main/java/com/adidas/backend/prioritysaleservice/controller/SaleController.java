package com.adidas.backend.prioritysaleservice.controller;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.ISaleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sale")
@Slf4j
public class SaleController {
   
    @Autowired
    private ISaleService saleService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseEntity<?> createSale(SaleFormDto sale) throws InterruptedException{
        try {
            log.info(String.format("Listener executed createMember({%s})", sale.toString()));
            saleService.createSale(sale);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error in message: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }    
    
    @GetMapping
    public ResponseEntity<List<SaleDto>> getSaleAll() throws InterruptedException{
        try{
            log.info(String.format("Listener executed getSaleAll"));
            return ResponseEntity.ok().body(saleService.getSales());
        }catch(Exception e){
            log.error("Error " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }   
    
    
}
