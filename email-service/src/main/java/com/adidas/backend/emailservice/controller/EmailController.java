package com.adidas.backend.emailservice.controller;

import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.SaleEmailInfoDto;
import com.adidas.backend.base.domain.exception.EmailNotSendedException;
import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.adidas.backend.emailservice.service.IEmailExtensionService;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {
    
    @Autowired
    private IEmailExtensionService emailService;    
    
    /*
    @PostMapping()
    public ResponseEntity post(EmailRequestDto dto){
        try{
            emailService.sendEmail(dto);
            return ResponseEntity.ok().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(EmailNotSendedException e){
            return ResponseEntity.internalServerError().build();
        }
    }*/
    
}
