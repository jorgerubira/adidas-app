package com.adidas.backend.publicservice.service.impl;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.MemberPointsDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.publicservice.feign.IMemberFeign;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberFeignService implements IMemberService{

    @Autowired
    private IMemberFeign memberFeign;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public void createMember(MemberFormDto member) throws ExternalException {
        try {
            memberFeign.post(member);
        } catch (Exception ex) {
            throw new ExternalException(ex);
        }
    }

    @Override
    public Member getMemberById(String id) throws IdNotFoundException, ExternalException {
        try {
            return mapper.map(memberFeign.getById(id).getBody(), Member.class);
        } catch (Exception ex) {
            throw new ExternalException(ex);
        }
    }

    @Override
    public List<MemberBasicInfoDto> getMembers() throws ExternalException {
        try{
            return memberFeign.getAll().getBody();    
        }catch(Exception ex){
            throw new ExternalException(ex);
        }
    }

    @Override
    public void incrementPoints(String id, Integer points) throws IdNotFoundException, ExternalException {
        try{
            memberFeign.addPoints(MemberPointsDto.builder().id(id).points(points).build());    
        }catch(Exception ex){
            throw new ExternalException(ex);
        }
    }

    @Override
    public void deleteMemberById(String id) throws IdNotFoundException, ExternalException {
        try{
            memberFeign.delete(id);
        }catch(Exception ex){
            throw new ExternalException(ex);
        }
    }
    
}
