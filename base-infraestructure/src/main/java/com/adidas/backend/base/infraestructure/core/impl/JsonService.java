package com.adidas.backend.base.infraestructure.core.impl;

import com.adidas.backend.base.domain.dto.JsonStringDto;
import com.adidas.backend.base.infraestructure.core.IJsonConverter;
import com.adidas.backend.base.infraestructure.core.IJsonDeserializer;
import com.adidas.backend.base.infraestructure.core.IJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


@Service
public class JsonService implements IJsonDeserializer, IJsonSerializer, IJsonConverter{
    
    @Override
    public String serialize(Object obj) throws JsonProcessingException{
        if (obj instanceof String){
            obj=JsonStringDto.builder().info(""+obj).build();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String res=objectMapper.writeValueAsString(obj);
        return res;
    }
    
    @Override
    public <T> T deserialize(String json, Class<T> type) throws JsonProcessingException{
        if (json.startsWith("\"")){
            json=json.replaceAll("\\\\\\\"", "\"");
            json=json.substring(1,json.length()-1);
        }
        var nameClass=type.getName();
        if ("java.lang.String".equals(nameClass)){
            ObjectMapper objectMapper = new ObjectMapper();
            JsonStringDto res=objectMapper.readValue(json, JsonStringDto.class);
            return (T)res.getInfo();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        T res=objectMapper.readValue(json, type);
        return res;
    }
    
    
    
}
