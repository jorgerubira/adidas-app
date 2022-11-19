package com.adidas.backend.adiclubservice.listener;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.events.MemberBasicInfoEvent;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.adidas.backend.base.infraestructure.core.impl.JsonService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class MemberListenerTest {
    
    @Mock
    private IMemberService memberService;
    
    @Spy
    private IJsonConverter json=new JsonService();

    @Spy
    private ModelMapper modelMapper;    
    
    @InjectMocks
    @Resource
    private MemberListener instance;      

    @Test
    public void testCreateMember() throws Exception {
        String message=json.serialize(MemberFormDto.builder()
                                                .name("test")
                                                .email("test@test.com")
                                                .build());
        instance.createMember(message);
        
        ArgumentCaptor<MemberFormDto> arg1=ArgumentCaptor.forClass(MemberFormDto.class);
        verify(memberService, times(1)).createMember(arg1.capture()); 
        assertEquals("test", arg1.getValue().getName() );
        assertEquals("test@test.com", arg1.getValue().getEmail() );
    }

    @Test
    public void testGetMember() throws Exception {
        when(memberService.getMemberById("1111")).thenReturn(Member.builder()
                                                                   .id("1111")
                                                                   .email("test@test.com")
                                                                   .name("test")
                                                                   .points(333)
                                                                   .build()); 
        
        when(memberService.getMemberById("1112")).thenThrow(IdNotFoundException.class); 
        
        String result=instance.getMember(json.serialize("1111"));
        MemberBasicInfoDto event= json.deserialize(result, MemberBasicInfoDto.class);
        
        assertEquals("1111", event.getId() );
        assertEquals("test", event.getName());
        assertEquals("test@test.com", event.getEmail());
        
        assertThrows(IdNotFoundException.class, ()->instance.getMember(json.serialize("1112")));
        
    }
    
}
