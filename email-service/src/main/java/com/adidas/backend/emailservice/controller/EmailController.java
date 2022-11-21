package com.adidas.backend.emailservice.controller;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleEmailInfoDto;
import com.adidas.backend.base.domain.exception.EmailNotSendedException;
import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import com.adidas.backend.base.domain.services.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.adidas.backend.emailservice.service.IEmailExtensionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {
    
    @Autowired
    private IEmailExtensionService emailExtService;
    
    @Autowired
    private IEmailService emailService;

    @PostMapping("/send_email")
    public ResponseEntity<?> sendEmailNotification(String link, String nameMember, String nameSale, String id, String state, String email) {
        try{
            emailExtService.sendEmail(EmailRequestDto.builder()
                                        .sale(SaleDto.builder().link(link).name(nameSale).state(state).build() )
                                        .member(MemberBasicInfoDto.builder().name(nameMember).id(id).email(email).build() )
                                        .build());
            return ResponseEntity.ok().build();
        }catch(Exception e){
            //TODO:
            e.printStackTrace();
                    
            log.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<EmailDto>> getEmailsError() throws InterruptedException{
        try{
            return ResponseEntity.ok().body(emailService.getErrors());
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }       

    
}
