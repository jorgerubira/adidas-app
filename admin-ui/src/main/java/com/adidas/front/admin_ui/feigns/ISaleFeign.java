package com.adidas.front.admin_ui.feigns;

import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="ISaleFeign", url = "${app.public-url}/api/v1/sale")
public interface ISaleFeign {

    @GetMapping
    public ResponseEntity<List<SaleDto>> getAll();

    @PostMapping
    public void post(@SpringQueryMap SaleFormDto sale);

    @PostMapping("/init_queue/{id}")
    public ResponseEntity<String> initQueue(@PathVariable String id);
    
}
