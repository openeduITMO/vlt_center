package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VlRepository extends CrudRepository<VirtLab, Long> {
    @Query("select vl from VirtLab vl where vl.dirName = :dirName")
    VirtLab findByDirName(@Param("dirName") String dirName);

    @Query("select vl from VirtLab vl where vl.url = :url")
    VirtLab findByUrl(@Param("url") String url);
}
