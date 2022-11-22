package com.adidas.backend.publicservice.feign;

import com.adidas.backend.base.domain.dto.EmailDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="IEmailFeign", url = "${app.priority-url}/api/v1/sale")
public interface IEmailFeign {
   
    @GetMapping()
    public ResponseEntity<List<EmailDto>> getEmailsError() throws InterruptedException;
    
}
