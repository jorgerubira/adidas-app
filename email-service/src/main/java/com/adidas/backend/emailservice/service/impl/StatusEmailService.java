package com.adidas.backend.emailservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IEmailService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.emailservice.dao.IStatusEmailRepository;
import com.adidas.backend.emailservice.entities.StatusEmail;
import com.adidas.backend.emailservice.service.IStatusEmailService;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class StatusEmailService implements IStatusEmailService, IEmailService{

    @Autowired
    private IMQService mq;
    
    @Autowired
    private IStatusEmailRepository statusRepository;
    
    public void notifyOnChange(String id){
        try { 
            mq.send(MQTopics.EMAIL_EVENT_ONCHANGE, id);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
        }
    }      
    
    @Override
    public String initSendingEmail(String idMember, String email, String link) {
        StatusEmail status=new StatusEmail();
        status.setIdMember(idMember);
        status.setEmail(email);
        status.setLink(link);
        status.setStatus("SENDING");
        status.setRegistrationDate(Instant.now());
        status=statusRepository.save(status);
        notifyOnChange(status.getId());
        return status.getId();
        
    }

    @Override
    public void completedSendingEmail(String id, String html) {
        statusRepository.completeEmail(id, html);
        notifyOnChange(id);
    }

    @Override
    public void errorSendingEmail(String id) {
        statusRepository.errorEmail(id);
        notifyOnChange(id);
    }

    public EmailDto convertDto(Map m){
        EmailDto res=new EmailDto();
        res.setName(""+m.get("name"));
        res.setLink(""+m.get("link"));
        res.setEmail(""+m.get("email"));
        res.setId(""+m.get("id"));
        return res;
    }
    
    @Override
    public List<EmailDto> getErrors() throws ExternalException {
        return statusRepository.getErrors().stream()
                                  .map(x->convertDto(x))
                                  .collect(Collectors.toList());        
    }


    
    
}
