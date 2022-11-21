/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.adidas.backend.prioritysaleservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="IQueueFeign", url = "${app.adiclub-url}/api/v1/queue")
public interface IQueueFeign {

    @PostMapping("/{idSale}")
    public ResponseEntity<?> createQueue(@PathVariable String idSale);

}
