package com.adidas.backend.prioritysaleservice.dao;

import com.adidas.backend.prioritysaleservice.entities.SaleEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ISaleRepository extends JpaRepository<SaleEntity, String>{

    public List<SaleEntity> findByState(String active);

    @Query(value = "update sales set state=:state where id=:idSale", nativeQuery = true)
    @Transactional
    @Modifying
    public void updateSetState(String idSale, String state);    

    public Page<SaleEntity> findByOrderByRegistrationDateDesc(Pageable ofSize);
    
}
