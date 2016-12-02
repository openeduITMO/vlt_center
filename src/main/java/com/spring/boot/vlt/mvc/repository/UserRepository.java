package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Query("select u from User u where u.login = :login")
    public User findByLogin(@Param("login") String login);
}
