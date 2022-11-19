package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.services.ISaleService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.prioritysaleservice.dao.ISaleRepository;
import com.adidas.backend.prioritysaleservice.entities.SaleEntity;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepository;
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IMQService mq;

    public void notifyOnChange(String id){
        try { 
            mq.send(MQTopics.SALE_EVENT_ONCHANGE, id);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
        } 
    }  
    
    @Override
    public void createSale(SaleFormDto dto) throws ExternalException {
        SaleEntity member=SaleEntity.builder()
                            .name(dto.getName())
                            .link(dto.getLink())
                            .state("PENDING")
                            .registrationDate(Instant.now())
                            .build();
        saleRepository.save(member);
        notifyOnChange(member.getId());
    }
    @Override
    public List<SaleDto> getSales() throws ExternalException {
        return saleRepository.findByOrderByRegistrationDateDesc(Pageable.ofSize(100)) //Avoid with the memory. Limit the result by default
                               .getContent()
                               .stream()
                               .map(x->mapper.map(x, SaleDto.class))
                               .collect(Collectors.toList());        
    }

    

    
}
