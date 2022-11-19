package com.adidas.backend.prioritysaleservice.scheluder;

import com.adidas.backend.prioritysaleservice.service.IPriorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class NotificationScheduler {

    @Autowired
    private IPriorityService priorityService;
    
    private boolean sending=false;
    
    @Scheduled(cron = "1 * * * * *")  //Each one minute
    public void sendSaleNotifications(){
        log.info("Scheduled emails");
        if (sending==false){
            try{
                sending=true;
                priorityService.sendNotificationsAllSales();
            }catch(Exception e){
                log.error("Error : " + e.getMessage());
            }finally{
                sending=false;
            }
            
        }
        
    }
    
    
}
