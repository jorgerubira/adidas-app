package com.adidas.backend.adiclubservice.service.impl;

import com.adidas.backend.adiclubservice.dao.IMemberRepository;
import com.adidas.backend.adiclubservice.entities.MemberEntity;
import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.infraestructure.core.IJsonSerializer;
import com.adidas.backend.base.infraestructure.core.IMQService;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {
    
    @Mock
    private IMemberRepository memberRepository;
    
    @Spy
    private IJsonSerializer serialize;

    @Spy
    private IMQService mq;

    @Spy
    private ModelMapper mapper;
    
    @InjectMocks
    @Resource
    private MemberServiceImpl instance;    
    
    @Test
    public void testCreateMember() throws Exception {
        
        instance.createMember(MemberFormDto.builder().name("test").email("test@test.com").build());
        ArgumentCaptor<MemberEntity> arg=ArgumentCaptor.forClass(MemberEntity.class);
        verify(memberRepository, times(1)).save(arg.capture()); 
        assertEquals("test", arg.getValue().getName());
        assertEquals("test@test.com", arg.getValue().getEmail());
        
        ArgumentCaptor<String> arg2=ArgumentCaptor.forClass(String.class);
        verify(mq).send(arg2.capture(), any());
        assertEquals(MQTopics.GLOBAL_UPDATE, arg2.getValue());

    }

    @Test
    public void testGetMemberById() throws Exception {
        when(memberRepository.findById("1111")).thenReturn(Optional.of(MemberEntity.builder()
                                                                             .id("1111")
                                                                             .email("test@test.com")
                                                                             .name("test")
                                                                             .points(333)
                                                                             .build())); 
        when(memberRepository.findById("1112")).thenReturn(Optional.empty()); 
        
        //MemberServiceImpl instance = new MemberServiceImpl();
        Member result = instance.getMemberById("1111");
        assertNotNull(result);
        assertEquals("1111", result.getId());
        assertEquals("test@test.com", result.getEmail());
        assertEquals("test", result.getName());
        assertEquals(333, result.getPoints());

        assertThrows(IdNotFoundException.class, ()->instance.getMemberById("1112"));

        
    }
    
}
