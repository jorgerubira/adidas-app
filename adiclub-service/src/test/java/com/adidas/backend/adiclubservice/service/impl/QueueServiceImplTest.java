package com.adidas.backend.adiclubservice.service.impl;

import com.adidas.backend.adiclubservice.dao.IQueueRepository;
import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.infraestructure.core.IMQService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import javax.annotation.Resource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QueueServiceImplTest {

    @Mock
    private IQueueRepository queueRepository;
    
    @Mock
    private IMQService mq;
    
    @InjectMocks
    @Resource
    private QueueServiceImpl instance;        

    @Test
    public void testCreateQueueFromMembers() throws Exception {
        instance.initQueue("1111"); 
        ArgumentCaptor<String> arg=ArgumentCaptor.forClass(String.class);
        verify(queueRepository).createQueue(arg.capture()); 
        assertEquals("1111", arg.getValue()); 
        
        verify(mq, times(2)).send(any(),any());

    }
    
}
