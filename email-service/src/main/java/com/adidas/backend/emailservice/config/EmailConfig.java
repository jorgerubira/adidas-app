package com.adidas.backend.emailservice.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class EmailConfig {
    
    @Value("${email.transport.protocol:smtp}")
    private String protocol;
    
    @Value("${email.smtp.server:ssl0.ovh.net}")
    private String server;

    @Value("${email.smtp.port:587}")
    private Integer port;
    
    @Value("${email.smtp.username:adidas@jorgerubira.com}")
    private String username;

    @Value("${email.smtp.password:randompass}")
    private String password;

    @Value("${email.smtp.auth:true}")
    private String auth;

    @Value("${email.smtp.starttls.enable:true}")
    private String starttls;

    @Value("${email.smtp.ssl-trust:ssl0.ovh.net}")
    private String sslTrust;    

    @Value("${email.smtp.debug:false}")
    private String debug;    
    
    @Bean
    public JavaMailSender getJavaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("ssl0.ovh.net");
            mailSender.setPort(587);

            mailSender.setUsername("adidas@jorgerubira.com");
            mailSender.setPassword("randompass");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", protocol);
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.ssl.trust", sslTrust );
            props.put("mail.debug", debug);
            return mailSender;
    }    
    
}
