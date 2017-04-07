package com.spring.boot.vlt.mvc.repository.rlcp;

import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateRlcpRepository extends CrudRepository<GenerateRlcp, Long>{
    @Query("select g from GenerateRlcp g where g.session = :session")
    GenerateRlcp findBySession(@Param("session") String session);
}
