package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface VlRepository extends CrudRepository<VirtLab, Long> {
    @Query("select vl from VirtLab vl where vl.dirName = :dirName")
    VirtLab findByDirName(@Param("dirName") String dirName);

    @Query("select vl from VirtLab vl where vl.url = :url")
    VirtLab findByUrl(@Param("url") String url);

    @Query("select vl from VirtLab vl join vl.authors author where author.login = :login")
    Set<VirtLab> findByAuthor(@Param("login") String login);

    @Query("select vl from " +
            "VirtLab vl join vl.authors author " +
            "where vl.isPublic = true and author.login != :login and " +
                "vl.id not in (select reg.id from User user join user.register reg where user.login = :login)")
    Set<VirtLab> getPublicVlList(@Param("login") String login);
}
