/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adidas.front.admin_ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZOkController {
    
    @Value("${spring.profiles.active:none}")
    private String activeProfile;    
    
    @GetMapping("/check_ok")
    public ResponseEntity<String> ok() {
      return ResponseEntity
          .ok()
          .body("It's ok");
    }  
    
    
    @GetMapping("/profile")
    public String profile(){
        return activeProfile;
    }
}
