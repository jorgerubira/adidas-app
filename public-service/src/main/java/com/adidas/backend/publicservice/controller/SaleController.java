package com.adidas.backend.publicservice.controller;

import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IQueueService;
import com.adidas.backend.base.domain.services.ISaleService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/sale")
@Slf4j
public class SaleController {
    
    @Autowired 
    private ISaleService saleService;

    @Autowired 
    private IQueueService queueService;
    
    @Operation(summary="Return a list of sales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="Return a list", 
                        content = { @Content(mediaType="application/json", 
                                            schema=@Schema(implementation = SaleDto[].class) ) } ),
        @ApiResponse(responseCode = "404", description="Id not found", content = { @Content}),
        @ApiResponse(responseCode = "405", description="Data not available", content = { @Content})
    })      
    @GetMapping
    public ResponseEntity<List<SaleDto>> getAll(){
        try{
            List<SaleDto> result=saleService.getSales(); 
            return ResponseEntity.ok().body(result);
        }catch(IdNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            log.error("Error " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary="Insert a new sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The sale is created", content = { @Content() } ),
        @ApiResponse(responseCode = "405", description="Operation not available", content = { @Content})
    })       
    @PostMapping
    public ResponseEntity post(SaleFormDto sale){
        try{
            saleService.createSale(sale);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    
}
