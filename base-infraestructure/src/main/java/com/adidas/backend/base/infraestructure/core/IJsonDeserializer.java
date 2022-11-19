package com.adidas.backend.base.infraestructure.core;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface IJsonDeserializer {
    public <T> T deserialize(String json, Class<T> type) throws JsonProcessingException;
}
