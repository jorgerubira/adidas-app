package com.adidas.backend.base.domain.entities;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberQueue {
    
    private String id;
    private String idMember;
    private Integer points;
    private Instant registrationDate;
    private String idSale;
    
}
