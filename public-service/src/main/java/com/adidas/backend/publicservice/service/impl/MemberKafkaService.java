package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.MemberPointsDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Profile("kafka")
public class MemberKafkaService implements IMemberService{

    @Autowired
    private IMQService mq;
    
    @Override
    public void createMember(MemberFormDto member) throws ExternalException {
        try {
            mq.send(MQTopics.MEMBER_CREATE, member);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error " + ex.getMessage());
            throw new ExternalException(ex);
        }
    }

    @Override
    public Member getMemberById(String id) throws IdNotFoundException, ExternalException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<MemberBasicInfoDto> getMembers() throws ExternalException {
        try {
            return List.of(mq.receive(MQTopics.MEMBER_GET_ALL, MemberBasicInfoDto[].class));
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }
    }

    @Override
    public void incrementPoints(String id, Integer points) throws IdNotFoundException, ExternalException {
        try {
            mq.send(MQTopics.MEMBER_ADD_POINTS, MemberPointsDto.builder().id(id).points(points).build() );
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
            throw new ExternalException(ex);
        }
    }

    @Override
    public void deleteMemberById(String id) throws IdNotFoundException, ExternalException {
        try {
            mq.send(MQTopics.MEMBER_DELETE, id);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            log.error("Error : " + ex.getMessage());
        }
    }
    
}
