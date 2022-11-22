package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.prioritysaleservice.dao.IMemberQueueRepository;
import com.adidas.backend.prioritysaleservice.dao.ISaleRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QueueServiceImpl implements IQueueService{

    @Autowired
    private IMQService mq;
    
    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private IMemberQueueRepository queueRepository;

    public void notifyOnChange(){
        CompletableFuture.runAsync(()-> {
            try{
                mq.send(MQTopics.GLOBAL_UPDATE, "Sale");
            }catch(Exception ex){
                log.error("Error : " + ex.getMessage());
            }
        });
    }     
    
    @Override
    public void initQueue(String idSale) throws ExternalException {
        saleRepository.updateSetState(idSale, "ACTIVE");
        notifyOnChange();
    }

    @Override
    public void pauseQueue(String idSale) throws ExternalException {
        saleRepository.updateSetState(idSale, "PAUSE");
        notifyOnChange();
    }

    @Override
    public void restartQueue(String idSale) throws ExternalException {
        saleRepository.updateSetState(idSale, "ACTIVE");
        notifyOnChange();
    }

    public QueueDto convertDto(Map m){
        QueueDto res=new QueueDto();
        res.setName(""+m.get("name"));
        res.setLink(""+m.get("link"));
        res.setSale(""+m.get("sale"));
        try{
            res.setPoints(Integer.parseInt(""+m.get("points")));
        }catch(Exception e){}
        return res;
    }
    
    @Override
    public List<QueueDto> getQueue() throws ExternalException {
        return queueRepository.getQueue().stream() 
                                  .map(x->convertDto(x))
                                  .collect(Collectors.toList());
    }
    
    
    
}
