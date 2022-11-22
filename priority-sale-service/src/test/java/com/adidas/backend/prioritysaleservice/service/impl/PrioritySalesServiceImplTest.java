package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.prioritysaleservice.dao.IMemberQueueRepository;
import com.adidas.backend.prioritysaleservice.dao.ISaleRepository;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PrioritySalesServiceImplTest {
    
    @Spy
    private IMQService mq;

    @Mock
    private IMemberQueueRepository queueRepository;
    
    @Mock
    private ISaleRepository saleRepository;

    @Spy
    private ModelMapper mapper;    

    @InjectMocks
    @Resource
    private PrioritySalesKafkaServiceImpl instance;
    
    
}
