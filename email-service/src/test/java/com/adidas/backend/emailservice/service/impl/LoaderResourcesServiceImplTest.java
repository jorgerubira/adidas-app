package com.adidas.backend.emailservice.service.impl;

import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoaderResourcesServiceImplTest {
    
    @InjectMocks
    @Resource
    private LoaderResourcesServiceImpl instance;

    @Test
    public void testLoadResource() throws Exception {
        String html=instance.loadResource("sale_notification");
        assertNotNull(html);
        assertNotEquals(0, html.length());

        assertThrows(ResourceNotFoundException.class, ()->instance.loadResource("html_not_found"));
    }
    
}
