package com.adidas.backend.base.domain.services;

import com.adidas.backend.base.domain.exception.ExternalException;


//A service can only init the queue.
public interface IQueueInitService {
    public void initQueue(String idSale) throws ExternalException;
}
