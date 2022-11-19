package com.adidas.backend.prioritysaleservice.listener;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.domain.services.ISaleService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleListener {
    
    @Autowired
    private IJsonConverter json;
    
    @Autowired
    private ISaleService saleService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @KafkaListener(topics = MQTopics.SALE_CREATE)
    public void createSale(String pSale) throws InterruptedException{
        try {
            SaleFormDto sale=json.deserialize(pSale, SaleFormDto.class);
            log.info(String.format("Listener executed createMember({%s})", sale.toString()));
            saleService.createSale(sale);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error in message: " + ex.getMessage());
        }
    }    
    
    @KafkaListener(topics = MQTopics.SALE_GET_ALL)
    @SendTo
    public String getSaleAll() throws InterruptedException{
        try{
            try{
                log.info(String.format("Listener executed getSaleAll"));
                var result=saleService.getSales();
                return json.serialize(result);
            }catch(IdNotFoundException e){
                return json.serialize(MemberBasicInfoEvent.builder().status(403).message("Id not found").build());
            }
        }catch(JsonProcessingException | ExternalException e){
            log.error("Error " + e.getMessage());
            return "";
        }
    }   
    

    
}
