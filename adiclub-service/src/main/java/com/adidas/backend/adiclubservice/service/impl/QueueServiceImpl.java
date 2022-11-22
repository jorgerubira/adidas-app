
package com.adidas.backend.adiclubservice.service.impl;

import com.adidas.backend.adiclubservice.dao.IQueueRepository;
import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IQueueInitService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QueueServiceImpl implements IQueueInitService{

    @Autowired
    private IMQService mq;

    @Autowired
    private IQueueRepository queueRepository;
    
    public void notifyOnChange(){
        CompletableFuture.runAsync(()-> {
            try{
                mq.send(MQTopics.GLOBAL_UPDATE, "Queue");
            }catch(Exception ex){
                log.error("Error : " + ex.getMessage());
            }
        });
    }  
    
    @Override
    public void initQueue(String idSale) throws ExternalException {
        try {
            //It's very expensive to send a full table by Json. We make a table Queue from this service
            queueRepository.createQueue(idSale);
            notifyOnChange();
        } catch (Exception ex) {
            log.error("Error " + ex.getMessage());
        }
    }

    
}
