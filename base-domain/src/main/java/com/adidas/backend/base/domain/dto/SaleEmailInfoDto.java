package com.adidas.backend.base.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaleEmailInfoDto {
    
    private String idMember;
    private String nameMember;
    private String email;
    private String link;
    private String title;
    private String nameSale;
    
}
