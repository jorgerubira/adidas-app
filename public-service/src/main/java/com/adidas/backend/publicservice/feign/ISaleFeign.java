package com.adidas.backend.publicservice.feign;

import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.backend.base.domain.services.ISaleService;
import feign.QueryMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@FeignClient(name="ISaleFeign", url = "${app.priority-url}/api/v1/sale")
public interface ISaleFeign {
   
    @PostMapping
    public ResponseEntity<?> createSale(@SpringQueryMap SaleFormDto sale) ;
    
    @GetMapping
    public ResponseEntity<List<SaleDto>> getSaleAll() ;
    
}
