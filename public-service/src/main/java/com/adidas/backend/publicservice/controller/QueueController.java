package com.adidas.backend.publicservice.controller;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
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
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "Queue management",
                version = "v1",
                description = "This API allow to create and management queues",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(url = "http://www.adidas.com", name = "Adidas", email = "contact@adidas.es")
        )
)

@RestController
@RequestMapping("/api/v1/queue")
@Slf4j
public class QueueController {
    
    @Autowired
    private IQueueService queueService;
    
    @Operation(summary="Create a queue from the members")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The queue is created", content = { @Content() } ),
        @ApiResponse(responseCode = "404", description="Operation not available", content = { @Content})
    })       
    @PutMapping("/init/id_sale/{id}")
    public ResponseEntity<String> initQueue(@PathVariable String id){
        try{
            queueService.initQueue(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary="Pause sending emails by a sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The sending email of the sale is paused", content = { @Content() } ),
        @ApiResponse(responseCode = "404", description="Operation not available", content = { @Content})
    })       
    @PutMapping("/pause/id_sale/{id}")
    public ResponseEntity<String> pauseQueue(@PathVariable String id){
        try{
            queueService.pauseQueue(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary="Resume sending emails by a sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The sending email of the sale is resume", content = { @Content() } ),
        @ApiResponse(responseCode = "404", description="Operation not available", content = { @Content})
    })       
    @PutMapping("/restart/id_sale/{id}")
    public ResponseEntity<String> restartQueue(@PathVariable String id){
        try{
            queueService.restartQueue(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary="Return information about the pending emails in queues")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="Return a list", 
                        content = { @Content(mediaType="application/json", 
                                            schema=@Schema(implementation = QueueDto[].class) ) } ),
        @ApiResponse(responseCode = "405", description="Data not available", content = { @Content})
    })       
    @GetMapping()
    public ResponseEntity<List<QueueDto>> getQueue(){
        log.info(String.format("Controller executed getQueue"));
        try{
            List<QueueDto> result=queueService.getQueue()
                                                .stream()
                                                .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }catch(ExternalException e){
            return ResponseEntity.internalServerError().build();
        }          
    }

    
}
