package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.Attempts;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AttemptsRepository extends CrudRepository<Attempts, Long> {
    @Query("select att from Attempts att where att.session = :session")
    Attempts foundBySession(@Param("session") String session);

    @Query("select vl from Attempts att, VirtLab vl where att.session = :session and vl = att.lab")
    VirtLab foundVlBySession(@Param("session") String session);

    @Query("select g from Attempts att, GenerateRlcp g where att.session = :session and g.attempt = att")
    GenerateRlcp foundGenerateBySession(@Param("session") String session);

    @Query("select c from Attempts att, CheckRlcp c where att.session = :session and c.attempt = att")
    CheckRlcp foundCheckBySession(@Param("session") String session);
}
