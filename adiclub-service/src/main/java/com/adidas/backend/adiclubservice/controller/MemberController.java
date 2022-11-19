package com.adidas.backend.adiclubservice.controller;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    
    @Autowired
    private IMemberService memberService;

    @Autowired
    private ModelMapper modelMapper;    
    
    @GetMapping("/{id}")
    public ResponseEntity<MemberBasicInfoDto> getById(@PathVariable String id){
        log.info(String.format("Controller executed getMember({%s})", id));
        try{
            Member member=memberService.getMemberById(id);
            MemberBasicInfoDto memberDto=modelMapper.map(member, MemberBasicInfoDto.class);
            return ResponseEntity.ok().body(memberDto);
        }catch(IdNotFoundException e){
            return ResponseEntity.status(403).build();
        }catch(ExternalException e){
            return ResponseEntity.status(405).build();
        }        
    }
    
    @PostMapping()
    public ResponseEntity post(MemberFormDto member){
        try{
            log.info(String.format("Controller executed createMember({%s})", member.toString()));
            memberService.createMember(member);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.status(403).build();
        }
    }

}
