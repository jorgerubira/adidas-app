package com.adidas.backend.base.domain.services;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import java.util.List;


public interface IQueueService extends IQueueInitService{
    
    public void pauseQueue(String idSale) throws ExternalException;
    public void restartQueue(String idSale) throws ExternalException;
    public List<QueueDto> getQueue() throws ExternalException;
    
}
