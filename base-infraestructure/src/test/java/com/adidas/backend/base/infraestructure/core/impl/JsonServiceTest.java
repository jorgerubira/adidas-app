package com.adidas.backend.base.infraestructure.core.impl;

import com.adidas.backend.base.domain.dto.SaleDto;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonServiceTest {

    @InjectMocks
    @Resource
    private JsonService jsonService;
    

    @Test
    public void testSerialize() throws Exception {
        SaleDto s=SaleDto.builder().id("111").build();
        String result=jsonService.serialize(s);
        assertNotEquals(0, result.length());
    }

    @Test
    public void testDeserialize() throws Exception {
        String json="{\"id\":\"1111\"}";
        SaleDto result=jsonService.deserialize(json, SaleDto.class);
        assertEquals("1111", result.getId());

        json="\"{\\\"id\\\":\\\"1111\\\"}\"";
        result=jsonService.deserialize(json, SaleDto.class);
        assertEquals("1111", result.getId());

    }
    
}