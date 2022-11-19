package com.adidas.backend.emailservice.service.impl;

import com.adidas.backend.base.domain.exception.ResourceNotFoundException;
import com.adidas.backend.emailservice.service.ILoaderResourcesService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class LoaderResourcesServiceImpl implements ILoaderResourcesService{

    @Override
    public String loadResource(String path) throws ResourceNotFoundException {
        try{
            Resource r = new ClassPathResource("\\emails\\" + path  + ".html");
            String email = new BufferedReader(
                               new InputStreamReader(r.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));         
            return email;
        }catch(IOException e){
            throw new ResourceNotFoundException(e);
        }
    }
    
}
