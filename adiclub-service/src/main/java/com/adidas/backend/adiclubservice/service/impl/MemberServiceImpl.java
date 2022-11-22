package com.adidas.backend.adiclubservice.service.impl;

import com.adidas.backend.adiclubservice.dao.IMemberRepository;
import com.adidas.backend.adiclubservice.entities.MemberEntity;
import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

@Service
@Slf4j
public class MemberServiceImpl implements IMemberService{

    @Autowired
    private IMQService mq;
    
    @Autowired
    private IMemberRepository memberRepository;
    
    @Autowired
    private ModelMapper mapper;

    public void notifyOnChange(){
        CompletableFuture.runAsync(()-> {
            try{
                mq.send(MQTopics.GLOBAL_UPDATE, "Member");
            }catch(Exception ex){
                log.error("Error : " + ex.getMessage());
            }
        });
    }  

    
    @Override
    public void createMember(MemberFormDto dto) throws ExternalException {
        MemberEntity member=MemberEntity.builder()
                            .name(dto.getName())
                            .email(dto.getEmail())
                            .points(0)
                            .registrationDate(Instant.now())
                            .build();
        memberRepository.save(member);
        notifyOnChange();
    }
    
    @Override
    public Member getMemberById(String id) throws IdNotFoundException, ExternalException{
        Optional<MemberEntity> result=memberRepository.findById(id);
        if (result.isPresent()){
            return mapper.map(result.get(), Member.class);
        }else{
            throw new IdNotFoundException();
        }
    }

    @Override
    @Transactional
    public void incrementPoints(String id, Integer points) throws IdNotFoundException, ExternalException {
        Optional<MemberEntity> result=memberRepository.findById(id);
        if (result.isPresent()){
            MemberEntity e=result.get();
            e.setPoints(e.getPoints()+points);
            memberRepository.save(e);
            memberRepository.flush();
        }else{
            throw new IdNotFoundException();
        }
        notifyOnChange();
    }

    @Override
    public List<MemberBasicInfoDto> getMembers() throws ExternalException {
        return memberRepository.findAll(Pageable.ofSize(100)) //Avoid with the memory. Limit the result by default
                               .getContent()
                               .stream()
                               .map(x->mapper.map(x, MemberBasicInfoDto.class))
                               .collect(Collectors.toList());
    }

    @Override
    public void deleteMemberById(String id) throws IdNotFoundException, ExternalException {
        if (memberRepository.existsById(id)){
            memberRepository.deleteById(id);
        }else{
            throw new IdNotFoundException();
        }
        notifyOnChange();
    }
    
}
