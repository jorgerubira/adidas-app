package com.adidas.backend.emailservice.service;

import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.SaleEmailInfoDto;
import com.adidas.backend.base.domain.exception.EmailNotSendedException;
import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import com.adidas.backend.base.domain.services.IEmailService;

public interface IEmailExtensionService {
    
    public String prepareEmailNotification(String name, String link) throws ResourceNotFoundException;
    public void sendHtml(String email, String title, String html) throws EmailNotSendedException;
    public void sendEmail(EmailRequestDto dto) throws ResourceNotFoundException, EmailNotSendedException;
    
    
}
