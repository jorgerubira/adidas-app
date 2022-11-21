package com.adidas.backend.publicservice.feign;

import com.adidas.backend.base.domain.dto.QueueDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name="IQueueFeign", url = "${app.priority-url}/api/v1/queue")
public interface IQueueFeign {
   
    @PostMapping("/start/{idSale}")
    public void queueStarted(@PathVariable String idSale) throws InterruptedException;

    @PutMapping("/pause/{idSale}")
    public void queuePause(@PathVariable String idSale) throws InterruptedException;
    
    @PutMapping("/restart/{idSale}")
    public void queueRestart(String idSale) throws InterruptedException;

    @GetMapping
    public ResponseEntity<List<QueueDto>> getQueueAll() throws InterruptedException;
    
}
