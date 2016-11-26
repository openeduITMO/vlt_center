package com.spring.boot.vlt.mvc.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Embeddable
    public static class Id implements Serializable{

        @Column(name = "user_id")
        protected Long userId;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        protected Role role;
        
        public Id() { }

        public Id(Long userId, Role role) {
            this.userId = userId;
            this.role = role;
        }
    }
    
    @EmbeddedId
    Id id = new Id();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable=false, updatable=false)
    protected Role role;

    public Role getRole() {
        return role;
    }

    public UserRole(Long userId, Role role) {
        this.id = new Id(userId, role);
        this.role = role;
    }
}
