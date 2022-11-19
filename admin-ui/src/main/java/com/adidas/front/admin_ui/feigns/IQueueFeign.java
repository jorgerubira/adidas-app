package com.adidas.front.admin_ui.feigns;

import com.adidas.backend.base.domain.dto.QueueDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name="IQueueFeign", url = "${app.public-url}/api/v1/queue")
public interface IQueueFeign {
    
    @PutMapping("/init/id_sale/{id}")
    public ResponseEntity<String> initQueue(@PathVariable String id);
    
    @PutMapping("/pause/id_sale/{id}")
    public ResponseEntity<String> pauseQueue(@PathVariable String id);

    @PutMapping("/restart/id_sale/{id}")
    public ResponseEntity<String> restartQueue(@PathVariable String id);
    
    @GetMapping()
    public ResponseEntity<List<QueueDto>> getQueue();      
}
