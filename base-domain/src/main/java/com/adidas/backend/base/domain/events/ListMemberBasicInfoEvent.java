package com.adidas.backend.base.domain.events;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import java.util.List;
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
public class ListMemberBasicInfoEvent {
    
    public int status;
    public String message;
    public List<MemberBasicInfoDto> info;
            
}
