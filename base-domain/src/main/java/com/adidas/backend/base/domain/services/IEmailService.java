package com.adidas.backend.base.domain.services;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import java.util.List;


public interface IEmailService {
    public List<EmailDto> getErrors() throws ExternalException;
}
