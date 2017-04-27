package com.spring.boot.vlt.mvc.repository;

import com.spring.boot.vlt.mvc.model.pojo_response.RegisterForVl;
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

    @Query("select u.register from User u where u.login = :login")
    Set<VirtLab> getUsersVirtLabs(@Param("login") String login);

    @Query("select new com.spring.boot.vlt.mvc.model.pojo_response.RegisterForVl(" +
            "u.login as login, generate.date as startDate, generate.attempt.session as session, check.response as result) " +
            "from Session att, GenerateRlcp generate, CheckRlcp check, User u join u.register vl " +
            "where vl.dirName = :dir and att.user.login = u.login and att.lab.dirName = vl.dirName and " +
            "att.session = generate.attempt.session " +
            "and check.attempt.session = generate.attempt.session")
    Set<RegisterForVl> getStudentForVl(@Param("dir") String dir);


    //    @Query("Select new com.spring.boot.vlt.mvc.model.pojo_response.RegisterForVl(" +
//            "res.login as login, res.date as startDate, a.session as session, c.response as result) "+
//            "from Session a, " +
//            "(select max(g.date) as date, u.login as login "+
//            "from VirtLab vl "+
////                "JOIN u.declaration ld ON (ld.id = vl.id) "+
//            "JOIN ATTEMPTS session ON (session.user.id in ld.authors AND session.lab.id = vl.id) "+
//            "JOIN GenerateRLCP g ON (g.attempt.id = session.id) "+
//            "JOIN Users u ON (u.id in ld.authors) "+
//            "WHERE vl.dir_name= :dir "+
//            "        Group by (u.login) "+
//            ") res "+
//            "JOIN User u ON (a.user.id = u.id) "+
//            "JOIN res ON (res.login = u.login) "+
//            "JOIN GenerateRLCP g ON (a.id = g.attempt.id AND res.date = g.date) "+
//            "JOIN CheckRLCP c ON (c.attempt.id= g.attempt.id)")
}
