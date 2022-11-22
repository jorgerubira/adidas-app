package com.adidas.front.admin_ui.controller;

import com.adidas.backend.base.domain.config.MQTopics;
import com.adidas.backend.base.domain.dto.EmailDto;
import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.QueueDto;
import com.adidas.backend.base.domain.dto.SaleDto;
import com.adidas.backend.base.domain.dto.SaleFormDto;
import com.adidas.front.admin_ui.feigns.IEmailFeign;
import com.adidas.front.admin_ui.feigns.IMemberFeign;
import com.adidas.front.admin_ui.feigns.IQueueFeign;
import com.adidas.front.admin_ui.feigns.ISaleFeign;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AdminController {
    
    private String lastVersion;

    @Autowired
    private IMemberFeign members;
    
    @Autowired
    private ISaleFeign sales;

    @Autowired
    private IQueueFeign queue;

    @Autowired
    private IEmailFeign emails;

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/list_members")
    @ResponseBody
    public ResponseEntity<List<MemberBasicInfoDto>> listMembers(){
        return members.getAll();
    }
    
    @PostMapping("/new_member")
    public String newMember(MemberFormDto member){
        log.info("Post " + member.getName());
        members.post(member); 
        return "redirect:/";
    }    
    
    @PutMapping("/add_point_member/{id}")
    @ResponseBody
    public ResponseEntity<String> addPointMember(@PathVariable String id){
        return members.addPointMember(id);
    }
    
    @PostMapping("/delete_member/{id}") 
    public String deleteMember(@PathVariable String id){
        log.info("Delete " + id);
        members.delete(id); 
        return "redirect:/";
    }       

    @GetMapping("/list_sales")
    @ResponseBody
    public ResponseEntity<List<SaleDto>> listSale(){
        return sales.getAll();
    }
    
    @PostMapping("/new_sale")
    public String newSale(SaleFormDto sale){
        log.info("Post " + sale.getName());
        sales.post(sale); 
        return "redirect:/";
    }    
    
    @PutMapping("/init_queue/{id}")
    @ResponseBody
    public ResponseEntity<String> initQueue(@PathVariable String id){
        return queue.initQueue(id);
    }

    @PutMapping("/pause_queue/{id}")
    @ResponseBody
    public ResponseEntity<String> pauseQueue(@PathVariable String id){
        return queue.pauseQueue(id);
    }

    @PutMapping("/restart_queue/{id}")
    @ResponseBody
    public ResponseEntity<String> restartQueue(@PathVariable String id){
        return queue.restartQueue(id);
    }

    @GetMapping("/list_queue")
    @ResponseBody
    public ResponseEntity<List<QueueDto>> listQueue(){
        return queue.getQueue();
    }

    @GetMapping("/list_emails_error")
    @ResponseBody
    public ResponseEntity<List<EmailDto>> listEmails(){
        return emails.getErrors();
    }
    
    @GetMapping("/last_version")
    @ResponseBody
    public String getLastVersion(){
        return lastVersion;
    }    
    
    @KafkaListener(topics = MQTopics.GLOBAL_UPDATE)
    public String notification(String mensaje) {
        lastVersion=UUID.randomUUID().toString();
        log.info("---------------------- Ha habido cambios ----------------");
        return mensaje;
    }    
    
    
    
    
}
