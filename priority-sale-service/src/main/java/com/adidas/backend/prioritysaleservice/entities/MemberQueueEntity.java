package com.adidas.backend.prioritysaleservice.entities;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "members_queues")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberQueueEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    private String idMember;
    private Integer points;
      
    private Instant registrationDate;
    private String idSale;
    
}
