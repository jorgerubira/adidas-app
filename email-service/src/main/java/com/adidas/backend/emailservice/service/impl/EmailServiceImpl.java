package com.adidas.backend.emailservice.service.impl;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.SaleEmailInfoDto;
import com.adidas.backend.base.domain.exception.EmailNotSendedException;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import com.adidas.backend.emailservice.service.ILoaderResourcesService;
import com.adidas.backend.emailservice.service.IStatusEmailService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.adidas.backend.emailservice.service.IEmailExtensionService;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailExtensionService{

    @Value("${email.smtp.username:adidas@jorgerubira.com}")
    private String username;
    
    @Autowired
    private JavaMailSender mail;
    
    @Autowired
    private ILoaderResourcesService loader;
    
    @Autowired
    private IStatusEmailService statusService;       

    public String prepareEmailNotification(String name, String link) throws ResourceNotFoundException{
        String html=loader.loadResource("sale_notification");
        html = html.replaceAll("\\{\\{name}}", (name + "").replaceAll("null", ""));
        html = html.replaceAll("\\{\\{link}}", (link + "").replaceAll("null", ""));
        return html;
    }
    
    public void sendHtml(String email, String title, String html) throws EmailNotSendedException{
        try {
            MimeMessage mensaje = mail.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(html, true);
            mail.send(mensaje); 
        } catch (MessagingException ex) {
            throw new EmailNotSendedException(ex);
        }
    }

    @Override
    public void sendEmail(EmailRequestDto dto) throws ResourceNotFoundException, EmailNotSendedException{
        String idEmail=""; 
        try{
            var m=dto.getMember();
            var s=dto.getSale();
            idEmail=statusService.initSendingEmail(m.getId(), m.getEmail(), s.getLink() );
            String html=prepareEmailNotification(m.getName(), s.getLink());
            sendHtml(m.getEmail(), "Adidas Club Member. New sale released : " + s.getName(), html);
            statusService.completedSendingEmail(idEmail, html);
        }catch(ResourceNotFoundException | EmailNotSendedException e){
            statusService.errorSendingEmail(idEmail);
            throw e;
        }
    }



    
}
