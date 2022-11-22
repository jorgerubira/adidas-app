/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IEmailService;
import com.adidas.backend.prioritysaleservice.feign.IEmailFeign;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService{

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
