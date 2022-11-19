package com.adidas.backend.base.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberBasicInfoDto {
    
    private String id;
    private String name;
    private String email;
    private Integer points;
}
