package com.adidas.backend.publicservice.controller;

import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IEmailService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "AdiMembers suscription",
                version = "v1",
                description = "This API allow to manage a queue and send emails to members",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(url = "http://www.adidas.com", name = "Adidas", email = "contact@adidas.es")
        )
)

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {
    
    @Autowired
    private IEmailService emailService;

    @Autowired
    private ModelMapper mapper;     
    
    @Operation(summary="Returns a list of emails with errors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="Return a list", 
                        content = { @Content(mediaType="application/json", 
                                            schema=@Schema(implementation = EmailDto[].class) ) } ),
        @ApiResponse(responseCode = "405", description="Data not available", 
                        content = { @Content})
    })
    @GetMapping("/state/error")
    public ResponseEntity<List<EmailDto>> getErrors(){
        log.info(String.format("Controller executed getErrors"));
        try{
            List<EmailDto> result=emailService.getErrors();
            return ResponseEntity.ok().body(result);
        }catch(ExternalException e){
            return ResponseEntity.status(405).build();
        }           
    }
    
}
