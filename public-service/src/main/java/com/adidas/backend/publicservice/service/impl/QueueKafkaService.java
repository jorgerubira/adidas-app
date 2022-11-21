package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("kafka")
public class QueueKafkaService implements IQueueService {
    
    @Autowired
    private IMQService mq;    
    
    @Override
    public void initQueue(String id) throws IdNotFoundException, ExternalException {
        try {
            mq.send(MQTopics.QUEUE_CREATE, id );
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }    
    }    

    @Override
    public void pauseQueue(String id) throws ExternalException {
        try {
            mq.send(MQTopics.QUEUE_PAUSE, id );
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }    
    }

    @Override
    public void restartQueue(String id) throws ExternalException {
        try {
            mq.send(MQTopics.QUEUE_RESTART, id );
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }    
    }

    @Override
    public List<QueueDto> getQueue() throws ExternalException {
        try {
            return List.of(mq.receive(MQTopics.QUEUE_GET_ALL, QueueDto[].class));
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }      
    }
}
