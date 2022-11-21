/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adidas.backend.prioritysaleservice.service.impl;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.MemberPointsDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.prioritysaleservice.feign.IMemberFeign;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements IMemberService{

    @Autowired
    private IMemberFeign memberFeign;
    
    @Autowired
    private ModelMapper mapper;
    
    @Override
    public void createMember(MemberFormDto member) throws ExternalException {
        ResponseEntity<?> res=memberFeign.post(member);
        if (!res.getStatusCode().is2xxSuccessful()){
            throw new ExternalException();
        }
    }

    @Override
    public Member getMemberById(String id) throws IdNotFoundException, ExternalException {
        ResponseEntity<MemberBasicInfoDto> res=memberFeign.getById(id);
        if (!res.getStatusCode().is2xxSuccessful()){
            throw new ExternalException();
        }else{
            return mapper.map(res.getBody(), Member.class) ;
        }
    }

    @Override
    public List<MemberBasicInfoDto> getMembers() throws ExternalException {
        ResponseEntity<List<MemberBasicInfoDto>> res=memberFeign.getAll();
        if (!res.getStatusCode().is2xxSuccessful()){
            throw new ExternalException();
        }else{
            return res.getBody();
        }
        
    }

    @Override
    public void incrementPoints(String id, Integer points) throws IdNotFoundException, ExternalException {
        MemberPointsDto dto=MemberPointsDto.builder().id(id).points(points).build();
        ResponseEntity<?> res=memberFeign.addPoints(dto);
        if (!res.getStatusCode().is2xxSuccessful()){
            throw new ExternalException();
        }
    }

    @Override
    public void deleteMemberById(String id) throws IdNotFoundException, ExternalException {
        ResponseEntity<?> res=memberFeign.delete(id);
        if (!res.getStatusCode().is2xxSuccessful()){
            throw new ExternalException();
        }
    }
    
}
