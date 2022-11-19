package com.adidas.backend.base.domain.events;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleEvent {
    
    public int status;
    public String message;
    public Sale info;
            
}
