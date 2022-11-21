package com.adidas.backend.publicservice.controller;

import com.adidas.backend.base.domain.dto.MemberBasicInfoDto;
import com.adidas.backend.base.domain.dto.MemberFormDto;
import com.adidas.backend.base.domain.entities.Member;
import com.adidas.backend.base.domain.exception.ExternalException;
import com.adidas.backend.base.domain.exception.IdNotFoundException;
import com.adidas.backend.base.domain.services.IMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    @Autowired
    private IMemberService memberService;

    @Autowired
    private ModelMapper mapper;    
    
    @Operation(summary="Find a member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="Return a list", 
                        content = { @Content(mediaType="application/json", 
                                            schema=@Schema(implementation = MemberBasicInfoDto.class) ) } ),
        @ApiResponse(responseCode = "404", description="Id not found", content = { @Content}),
        @ApiResponse(responseCode = "405", description="Data not available", content = { @Content})
    })    
    @GetMapping("/{id}")
    public ResponseEntity<MemberBasicInfoDto> getById(@PathVariable String id){
        log.info(String.format("Controller executed getMember({%s})", id));
        try{
            Member member=memberService.getMemberById(id);
            MemberBasicInfoDto memberDto=mapper.map(member, MemberBasicInfoDto.class);
            return ResponseEntity.ok().body(memberDto);
        }catch(IdNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(ExternalException e){
            return ResponseEntity.internalServerError().build();
        }        
    }
    
    @Operation(summary="Insert a new member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The member is created", content = { @Content() } ),
        @ApiResponse(responseCode = "405", description="Operation not available", content = { @Content})
    })    
    @PostMapping()
    public ResponseEntity post(@Valid MemberFormDto member){
        try{
            log.info(String.format("Controller executed createMember({%s})", member.toString()));
            memberService.createMember(member);
            return ResponseEntity.ok().build();
        }catch(ExternalException e){
            return ResponseEntity.status(405).build();
        }   
    }
    
    @Operation(summary="Returns a list of members")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="Return a list", 
                        content = { @Content(mediaType="application/json", 
                                            schema=@Schema(implementation = MemberBasicInfoDto[].class) ) } ),
        @ApiResponse(responseCode = "405", description="Data not available", content = { @Content})
    })   
    @GetMapping()
    public ResponseEntity<List<MemberBasicInfoDto>> getAll(){
        log.info(String.format("Controller executed getAll"));
        try{
            List<MemberBasicInfoDto> result=memberService.getMembers()
                                                .stream()
                                                .map(x->mapper.map(x, MemberBasicInfoDto.class))
                                                .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }catch(ExternalException e){
            return ResponseEntity.internalServerError().build();
        }           
    }    
    
    @Operation(summary="Increment the points of a member by a Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The points has been increased", content = { @Content() } ),
        @ApiResponse(responseCode = "404", description="Id not found", content = { @Content}),
        @ApiResponse(responseCode = "405", description="Operation not available", content = { @Content})
    })     
    @PutMapping("/add_point_member/{id}")
    public ResponseEntity addPointMember(@PathVariable String id){
        try{
            log.info(String.format("Controller executed putAddPointMember({%s})", id));
            memberService.incrementPoints(id, 10);
            return ResponseEntity.ok().build();
        }catch(IdNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(ExternalException e){
            return ResponseEntity.internalServerError().build();
        }   
    }   
    
    @Operation(summary="Delete a member by a Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description="The member has been deleted", content = { @Content() } ),
        @ApiResponse(responseCode = "404", description="Id not found", content = { @Content}),
        @ApiResponse(responseCode = "405", description="Operation not available", content = { @Content})
    })     
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        log.info(String.format("Controller executed deletedBy({%s})", id));
        try{
            memberService.deleteMemberById(id);
            return ResponseEntity.ok().build();
        }catch(IdNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(ExternalException e){
            return ResponseEntity.internalServerError().build();
        }       
    }    
    
}
