package com.adidas.backend.emailservice.dao;

import com.adidas.backend.emailservice.entities.StatusEmail;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IStatusEmailRepository extends JpaRepository<StatusEmail, String> {

    @Modifying
    @Query(value = "update StatusEmail se set se.status = 'COMPLETED', se.html=:html, se.registrationDate=CURDATE() where se.id=:id")
    public void completeEmail(String id, String html);

    @Modifying
    @Query(value = "update StatusEmail se set se.status = 'ERROR', se.registrationDate=CURDATE() where se.id=:id")
    public void errorEmail(String id);

    @Query(value = "SELECT se.id, m.name, se.link\n" +
                    "FROM status_emails se\n" +
                    "INNER JOIN members m ON (m.id=se.id_member)\n" +
                    "WHERE STATUS='ERROR' \n" + 
                    " order by se.registration_date desc " +
                    " LIMIT 100", nativeQuery = true)
    public List<Map> getErrors();
    
}
