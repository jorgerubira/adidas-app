package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailRequestDto;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.adidas.backend.prioritysaleservice.dao.IMemberQueueRepository;
import com.adidas.backend.prioritysaleservice.dao.ISaleRepository;
import com.adidas.backend.prioritysaleservice.entities.MemberQueueEntity;
import com.adidas.backend.prioritysaleservice.entities.SaleEntity;
import com.adidas.backend.prioritysaleservice.service.IPriorityService;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;

@RefreshScope
@Service
@Slf4j
@Profile("kafka")
public class PrioritySalesKafkaServiceImpl implements IPriorityService{
    
    @Autowired
    private IMQService mq;

    @Value("${app.emails-by-minute:10}")
    private int emailsByMinute;
    
    @Autowired
    private IMemberQueueRepository queueRepository;
    
    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private ModelMapper mapper;

    public void notifyOnChangeQueue(String id){
        try { 
            mq.send(MQTopics.QUEUE_EVENT_ONCHANGE, id);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
        }
    }    

    public void notifyOnChangeSale(String id){
        try { 
            mq.send(MQTopics.SALE_EVENT_ONCHANGE, id);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void sendNotifications(String idSale) {
        var optSale=saleRepository.findById(idSale);
        if (optSale.isEmpty()){ //The sale is deleted
            queueRepository.deleteByIdSale(idSale);
            notifyOnChangeSale(idSale);
            return; //Don't send emails.
        }
        var sale=mapper.map(optSale.get(), SaleDto.class);
        List<MemberQueueEntity> members=queueRepository.findByIdSaleOrderByPointsDescRegistrationDate(idSale, Pageable.ofSize(emailsByMinute));
        if (members.size()==0){ //All emails are sended. It's completed
            saleRepository.updateSetState(idSale, "COMPLETED"); 
            notifyOnChangeSale(idSale);
            return;
        }
        members.forEach(m->{
            try {
                //Get member's information
                MemberBasicInfoDto member=mq.sendAndReceive(MQTopics.MEMBER_GET, m.getIdMember(), MemberBasicInfoDto.class);
                mq.send(MQTopics.EMAIL_SEND, EmailRequestDto.builder()
                        .member(member)
                        .sale(sale)
                        .build());
                queueRepository.deleteById(m.getId()); //Delete of list.
                notifyOnChangeQueue(m.getId());
            } catch (InterruptedException | ExecutionException ex) {
                log.error("Error : " + ex.getMessage());
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
    
}

