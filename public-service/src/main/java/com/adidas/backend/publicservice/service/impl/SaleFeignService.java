package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.ISaleService;
import com.adidas.backend.publicservice.feign.ISaleFeign;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SaleFeignService implements ISaleService{

    @Autowired
    private ISaleFeign saleFeign;
    
    @Override
    public void createSale(SaleFormDto sale) throws ExternalException {
        saleFeign.createSale(sale);
    }

    @Override
    public List<SaleDto> getSales() throws ExternalException {
        return saleFeign.getSaleAll().getBody();
    }



}
