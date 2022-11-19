package com.adidas.backend.adiclubservice.listener;

import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.adidas.backend.base.infraestructure.core.impl.JsonService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class QueueListenerTest {
    
    @Mock
    private IQueueService queueService;
    
    @Spy
    private ModelMapper modelMapper;
    
    @Spy
    private IJsonConverter json=new JsonService();

    @InjectMocks
    @Resource
    private QueueListener instance;      

    @Test
    public void testCreateQueryMembers() throws Exception {
        instance.createQueryMembers(json.serialize("1111"));
        
        ArgumentCaptor<String> arg=ArgumentCaptor.forClass(String.class);
        verify(queueService, times(1)).initQueue(arg.capture()); 
        assertEquals("1111", arg.getValue() );
        
    }
    
}
