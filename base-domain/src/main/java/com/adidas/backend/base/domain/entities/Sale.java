package com.adidas.backend.base.domain.entities;

import java.io.Serializable;
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
public class Sale implements Serializable{
    
    private String id;
    private String name;    //Name of sale
    private String state;   //PENDING,ACTIVE,PAUSED,FINISHED
    private String link;    //Link to information    
    
}
