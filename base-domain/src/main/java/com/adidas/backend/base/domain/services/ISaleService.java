package com.adidas.backend.base.domain.services;

import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import java.util.List;


public interface ISaleService {
    public void createSale(SaleFormDto sale) throws ExternalException;
    public List<SaleDto> getSales() throws ExternalException;
}
