package com.adidas.backend.adiclubservice.dao;

import com.adidas.backend.adiclubservice.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMemberRepository extends JpaRepository<MemberEntity, String> {
    
}
