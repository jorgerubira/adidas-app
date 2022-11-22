package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.prioritysaleservice.dao.IMemberQueueRepository;
import com.adidas.backend.prioritysaleservice.dao.ISaleRepository;
import com.adidas.backend.prioritysaleservice.entities.MemberQueueEntity;
import com.adidas.backend.prioritysaleservice.entities.SaleEntity;
import com.adidas.backend.prioritysaleservice.feign.IEmailFeign;
import com.adidas.backend.prioritysaleservice.feign.IMemberFeign;
import com.adidas.backend.prioritysaleservice.service.IPriorityService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;

@RefreshScope
@Service
@Slf4j
public class PrioritySalesFeignServiceImpl implements IPriorityService{

    @Autowired
    private IMQService mq;
    
    @Autowired
    private IEmailFeign emailFeign;
    
    @Autowired
    private IMemberFeign memberFeign;

    @Value("${app.emails-by-minute:10}")
    private int emailsByMinute;
    
    @Autowired
    private IMemberQueueRepository queueRepository;
    
    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public void sendNotifications(String idSale) {
        var optSale=saleRepository.findById(idSale);
        if (optSale.isEmpty()){ //The sale is deleted
            queueRepository.deleteByIdSale(idSale);
            notifyOnChange();
            return; //Don't send emails.
        }
        var sale=mapper.map(optSale.get(), SaleDto.class);
        List<MemberQueueEntity> members=queueRepository.findByIdSaleOrderByPointsDescRegistrationDate(idSale, Pageable.ofSize(emailsByMinute));
        if (members.size()==0){ //All emails are sended. It's completed
            saleRepository.updateSetState(idSale, "COMPLETED"); 
            notifyOnChange();
            return;
        }
        members.forEach(m->{
            try {
                //Get member's information
                MemberBasicInfoDto member=memberFeign.getById(m.getIdMember()).getBody();

                emailFeign.sendEmailNotification(sale.getLink(), member.getName(), sale.getName(), sale.getId(), sale.getState(), member.getEmail());
                
                queueRepository.deleteById(m.getId()); //Delete of list.
                //notifyOnChangeQueue(m.getId()); 
            } catch (Exception e){
                log.error("Error : " + e.getMessage());
            }
        });
        
    }

    @Override
    public void sendNotificationsAllSales() {
        List<SaleEntity> sales=saleRepository.findByState("ACTIVE");
        sales.forEach(x->sendNotifications(x.getId()));
    }
    
    public void notifyOnChange(){
        CompletableFuture.runAsync(()-> {
            try{
                mq.send(MQTopics.GLOBAL_UPDATE, "Email");
            }catch(Exception ex){
                log.error("Error : " + ex.getMessage());
            }
        });
    }      

}

