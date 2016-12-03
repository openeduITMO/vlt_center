package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Query("select u from User u where u.login = :login")
    public User findByLogin(@Param("login") String login);

    @Query("select u.labs from User u where u.login = :login")
    public Set<VirtLab> getUserVirtLabs(@Param("login") String login);
}
