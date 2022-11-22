package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IEmailService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.publicservice.feign.IEmailFeign;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailFeignService implements IEmailService {

    @Autowired
    private IEmailFeign emailFeign;
    
    @Override
    public List<EmailDto> getErrors() throws ExternalException {
        try{
            return emailFeign.getEmailsError().getBody();
        }catch(Exception e){
            throw new ExternalException(e);
        }
    }
    
    
}
