package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRoleRepository extends CrudRepository<UserRole, UserRole.Id> {
    @Query("select u_r.role from UserRole u_r where u_r.id.userId = :userId")
    Set<Role> findAllRoleForUser(@Param("userId") Long userId);

}
