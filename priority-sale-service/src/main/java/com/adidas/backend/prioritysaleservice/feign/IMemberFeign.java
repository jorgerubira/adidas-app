package com.adidas.backend.prioritysaleservice.feign;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.dto.MemberPointsDto;
import feign.QueryMap;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="IMemberFeign", url = "${app.adiclub-url}/api/v1/member")
public interface IMemberFeign {
    
    @GetMapping("/{id}")
    public ResponseEntity<MemberBasicInfoDto> getById(@PathVariable String id);
    
    @PostMapping()
    public ResponseEntity post(@SpringQueryMap MemberFormDto member);
    
    @GetMapping()
    public ResponseEntity<List<MemberBasicInfoDto>> getAll();
    
    @PostMapping("/addPoints")
    public ResponseEntity<?> addPoints(@SpringQueryMap MemberPointsDto points);
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id);
    
}
