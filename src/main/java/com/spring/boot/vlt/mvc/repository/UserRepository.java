package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.pojo_response.DeclarationForVl;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.login = :login")
    User findByLogin(@Param("login") String login);

    @Query("select u.declaration from User u where u.login = :login")
    Set<VirtLab> getUsersVirtLabs(@Param("login") String login);

    @Query("select new com.spring.boot.vlt.mvc.model.pojo_response.DeclarationForVl(" +
            "u.login as login, generate.date as startDate, generate.attempt.session as session, check.response as result) " +
            "from Attempts att, GenerateRlcp generate, CheckRlcp check, User u join u.declaration vl " +
            "where vl.dirName = :dir and att.user.login = u.login and att.lab.dirName = vl.dirName and " +
            "att.session = generate.attempt.session " +
            "and check.attempt.session = generate.attempt.session")
    Set<DeclarationForVl> getStudentForVl(@Param("dir") String dir);
}
