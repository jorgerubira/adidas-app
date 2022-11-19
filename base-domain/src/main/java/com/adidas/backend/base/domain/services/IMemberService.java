package com.adidas.backend.base.domain.services;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import java.util.List;


public interface IMemberService {

    public void createMember(MemberFormDto member) throws ExternalException;
    public Member getMemberById(String id) throws IdNotFoundException, ExternalException;
    public List<MemberBasicInfoDto> getMembers() throws ExternalException;
    public void incrementPoints(String id, Integer points) throws IdNotFoundException, ExternalException;
    public void deleteMemberById(String id) throws IdNotFoundException, ExternalException;
    
}
