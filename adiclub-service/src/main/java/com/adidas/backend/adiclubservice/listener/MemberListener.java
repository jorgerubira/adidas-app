package com.adidas.backend.adiclubservice.listener;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.MemberPointsDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberListener {
    
    @Autowired
    private IJsonConverter json;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @KafkaListener(topics = MQTopics.MEMBER_CREATE)
    public void createMember(String pMember) throws InterruptedException{
        try {
            MemberFormDto member=json.deserialize(pMember, MemberFormDto.class);
            log.info(String.format("Listener executed createMember({%s})", member.toString()));
            memberService.createMember(member);
        } catch (JsonProcessingException | ExternalException ex) {
            log.error("Error in message: " + ex.getMessage());
        }
    }    
    
    @KafkaListener(topics = MQTopics.MEMBER_GET)
    @SendTo
    public String getMember(String pIdMember) throws InterruptedException, IdNotFoundException{
        try{
            String idMember=json.deserialize(pIdMember, String.class);
            log.info(String.format("Listener executed getMember({%s})", idMember));
            Member member=memberService.getMemberById(idMember);
            MemberBasicInfoDto memberDto=modelMapper.map(member, MemberBasicInfoDto.class);
            return json.serialize(memberDto);
        }catch(JsonProcessingException | ExternalException e){
            log.error("Error " + e.getMessage());
            return "";
        }
    }     
    
    @KafkaListener(topics = MQTopics.MEMBER_GET_ALL)
    @SendTo
    public String getMemberAll() throws InterruptedException{
        try{
            try{
                log.info(String.format("Listener executed getMemberAll"));
                var result=memberService.getMembers();
                return json.serialize(result);
            }catch(IdNotFoundException e){
                return json.serialize(MemberBasicInfoEvent.builder().status(403).message("Id not found").build());
            }
        }catch(JsonProcessingException | ExternalException e){
            log.error("Error " + e.getMessage());
            return "";
        }
    }   
    
    @KafkaListener(topics = MQTopics.MEMBER_ADD_POINTS)
    public void addPoints(String pPoints) throws InterruptedException{
        try{
            try{
                var points=json.deserialize(pPoints, MemberPointsDto.class);
                log.info(String.format("Listener executed addPoints({%s}", points.getId()));
                memberService.incrementPoints(points.getId(), points.getPoints());
            }catch(IdNotFoundException e){
                log.error("Error " + e.getMessage());
            }
        }catch(JsonProcessingException | ExternalException e){
            log.error("Error " + e.getMessage());
        }
    }   

    
    @KafkaListener(topics = MQTopics.MEMBER_DELETE)
    @SendTo
    public void delete(String pIdMember) throws InterruptedException{
        try{
            try{
                String idMember=json.deserialize(pIdMember, String.class);
                log.info(String.format("Listener executed getMember({%s})", idMember));
                memberService.deleteMemberById(idMember);
            }catch(IdNotFoundException e){
                log.error("Error: " + e.getMessage());
            }
        }catch(JsonProcessingException | ExternalException e){
            log.error("Error " + e.getMessage());
        }
    }      
    
    
}
