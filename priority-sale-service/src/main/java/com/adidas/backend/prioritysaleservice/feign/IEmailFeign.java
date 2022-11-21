package com.adidas.backend.prioritysaleservice.feign;

import com.adidas.backend.base.domain.dto.EmailDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="IEmailFeign", url = "${app.email-url}/api/v1/email")
public interface IEmailFeign {

    @PostMapping("/send_email")
    public ResponseEntity<?> sendEmailNotification(@RequestParam String link, @RequestParam String nameMember, @RequestParam String nameSale, @RequestParam String id, @RequestParam String state, @RequestParam String email);
    
    @GetMapping()
    public ResponseEntity<List<EmailDto>> getEmailsError();


}
