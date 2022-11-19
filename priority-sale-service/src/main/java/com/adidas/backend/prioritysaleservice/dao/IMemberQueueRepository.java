package com.adidas.backend.prioritysaleservice.dao;

import com.adidas.backend.prioritysaleservice.entities.MemberQueueEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IMemberQueueRepository extends JpaRepository<MemberQueueEntity, String>{

    public List<MemberQueueEntity> findByIdSaleOrderByPointsDescRegistrationDate(String idSale, Pageable pageable);

    public void deleteByIdSale(String idSale); 

    @Query(value = "SELECT m.name , s.name sale, s.link, mq.points\n" +
                    "FROM members_queues mq\n" +
                    "INNER JOIN members m ON (m.id=mq.id_member)\n" +
                    "INNER JOIN sales s ON (s.id=mq.id_sale)\n" +
                    " ORDER BY mq.points desc, mq.registration_date \n" +
                    "LIMIT 100", nativeQuery = true)
    public List<Map> getQueue();
}
