package com.adidas.backend.emailservice.entities;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "status_emails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusEmail {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    private String idMember;
    private String email;
    private String link;
    private String html;
    private String status;
    private Instant registrationDate;
    
}
