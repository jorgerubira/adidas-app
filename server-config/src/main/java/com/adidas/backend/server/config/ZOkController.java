/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adidas.backend.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZOkController {
    
  @Value("${env:undefined}")
  public String environment;
    
  @GetMapping("/env")
  public ResponseEntity<String> env(){
    return ResponseEntity
        .ok()
        .body(environment);
  }
    
  @GetMapping("/check_ok")
  public ResponseEntity<String> ok() {
    return ResponseEntity
        .ok()
        .body("It's ok");
  }  
}
