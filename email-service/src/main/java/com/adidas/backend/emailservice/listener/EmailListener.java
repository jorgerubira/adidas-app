package com.adidas.backend.emailservice.listener;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.SaleEmailInfoDto;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.EmailNotSendedException;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import com.adidas.backend.base.domain.services.IEmailService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.adidas.backend.base.infraestructure.core.impl.MQService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import com.adidas.backend.emailservice.service.IEmailExtensionService;

@Component
@Slf4j
public class EmailListener {
    
    @Autowired
    private IJsonConverter json;

    @Autowired
    private IEmailExtensionService emailExtService;
    
    @Autowired
    private IEmailService emailService;
    
    @KafkaListener(topics = MQTopics.EMAIL_SEND)
    @Transactional
    public void sendEmailNotification(String pDto) {
        try{
            EmailRequestDto dto=json.deserialize(pDto, EmailRequestDto.class);
            emailExtService.sendEmail(dto);
        }catch(ResourceNotFoundException e){
            //TODO:
        }catch(EmailNotSendedException e){
            //TODO:
        }catch(JsonProcessingException e){
            log.error("Error " + e.getMessage());
        }
    }
    
    @KafkaListener(topics = MQTopics.EMAIL_GET_ERRORS)
    @SendTo
    public String getEmailsError() throws InterruptedException{
        try{
            try{
                log.info(String.format("Listener executed getEmailsError"));
                var result=emailService.getErrors();
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
