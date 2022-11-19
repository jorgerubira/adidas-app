package com.adidas.backend.emailservice.service;

import com.adidas.backend.base.domain.exception.ResourceNotFoundException;


public interface ILoaderResourcesService {
    
    public String loadResource(String path) throws ResourceNotFoundException;
    
}
