package com.adidas.front.admin_ui.feigns;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="IEmailFeign", url = "${app.public-url}/api/v1/email")
public interface IEmailFeign {
     
    @GetMapping("/state/error")
    public ResponseEntity<List<EmailDto>> getErrors();    
    
}
