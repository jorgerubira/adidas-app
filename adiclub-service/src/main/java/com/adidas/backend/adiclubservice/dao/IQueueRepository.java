package com.adidas.backend.adiclubservice.dao;

import com.adidas.backend.adiclubservice.entities.MemberQueueEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface IQueueRepository extends JpaRepository<MemberQueueEntity, String>{

    @Query(value = "INSERT INTO members_queues (id, id_member, id_sale, points, registration_date) \n" +
                    "SELECT UUID(), id, :idSale, points, registration_date\n" +
                    "FROM members", nativeQuery = true)
    @Modifying
    @Transactional
    public void createQueue(String idSale);
    
}
