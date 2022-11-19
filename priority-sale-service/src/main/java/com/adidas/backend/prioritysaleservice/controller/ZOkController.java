/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adidas.backend.prioritysaleservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ZOkController {

    @Value("${app.emails-by-minute:10}")
    private int emailsByMinute;    
    
    @GetMapping("/check_ok")
    public ResponseEntity<String> ok() {
        return ResponseEntity
                .ok()
                .body("It's ok");
    }

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity
                .ok()
                .body("Emails by minute: " + emailsByMinute);
    }

}
