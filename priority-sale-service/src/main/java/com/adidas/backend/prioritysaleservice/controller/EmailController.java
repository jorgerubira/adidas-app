package com.adidas.backend.prioritysaleservice.controller;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.services.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {
    
    @Autowired
    private IEmailService emailService;

    public ResponseEntity<List<EmailDto>> getEmailsError() throws InterruptedException{
        try{
            return ResponseEntity.ok().body(emailService.getErrors());
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }       

    
}
