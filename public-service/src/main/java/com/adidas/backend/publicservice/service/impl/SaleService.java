package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.ISaleService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SaleService implements ISaleService{

    @Autowired
    private IMQService mq;
    
    @Override
    public void createSale(SaleFormDto sale) throws ExternalException {
        try {
            mq.send(MQTopics.SALE_CREATE, sale);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error " + ex.getMessage());
        }
    }

    @Override
    public List<SaleDto> getSales() throws ExternalException {
        try {
            var result=List.of(mq.receive(MQTopics.SALE_GET_ALL, SaleDto[].class));
            return result;
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            return List.of();
        }    
    }



}
