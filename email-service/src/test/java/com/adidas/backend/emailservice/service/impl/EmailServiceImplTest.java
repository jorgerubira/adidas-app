package com.adidas.backend.emailservice.service.impl;

import com.adidas.backend.emailservice.service.ILoaderResourcesService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {
    
    
    @Mock
    private JavaMailSender mail;
    
    @Mock
    private ILoaderResourcesService loader;    
    
    @InjectMocks
    @Resource
    private EmailServiceImpl instance;        
    
    @Test
    public void testPrepareEmailNotification() throws Exception {
        when(loader.loadResource("sale_notification")).thenReturn("document {{name}} {{link}}");
        String html=instance.prepareEmailNotification("test", "http://test.com");
        assertEquals("document test http://test.com", html);
    }

    @Test
    public void testSendSaleNotification() throws Exception {
        //Not posible unit test. It sends a email.
    }
    
}
