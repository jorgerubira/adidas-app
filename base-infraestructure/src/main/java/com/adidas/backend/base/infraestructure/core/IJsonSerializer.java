package com.adidas.backend.base.infraestructure.core;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IJsonSerializer {
    public String serialize(Object obj) throws JsonProcessingException;
}
