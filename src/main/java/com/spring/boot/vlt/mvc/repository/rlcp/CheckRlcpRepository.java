package com.spring.boot.vlt.mvc.repository.rlcp;

import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRlcpRepository extends CrudRepository<CheckRlcp, Long>{
    @Query("select c from CheckRlcp c where c.session = :session")
    CheckRlcp findBySession(@Param("session") String session);
}
