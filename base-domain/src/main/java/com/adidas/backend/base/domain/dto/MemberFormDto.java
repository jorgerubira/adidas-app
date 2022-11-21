package com.adidas.backend.base.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDto {
    
    private String name;
    
    @NotBlank(message = "It's necessary fill the email")
    @Email(message = "Format must be email")
    private String email;
    
}
