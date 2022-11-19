package com.adidas.front.admin_ui.feigns;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="IMemberFeign", url = "${app.public-url}/api/v1/member")
public interface IMemberFeign {
    
    @GetMapping("/{id}")
    public MemberBasicInfoDto getById(@PathVariable String id);
    
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id);

    @PostMapping()
    public ResponseEntity post(@SpringQueryMap MemberFormDto member);
    
    @GetMapping()
    public ResponseEntity<List<MemberBasicInfoDto>> getAll();    
    
    @PutMapping("/add_point_member/{id}")
    public ResponseEntity addPointMember(@PathVariable String id);    

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id);
}
