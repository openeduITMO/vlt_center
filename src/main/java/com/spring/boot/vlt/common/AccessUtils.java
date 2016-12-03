package com.spring.boot.vlt.common;

import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.Role;

public class AccessUtils {

    public static boolean isStudent(UserContext userContext) {
        return userContext.getAuthorities().stream().filter(role ->
                Role.STUDENT.authority().equals(role.getAuthority())
        ).findFirst().isPresent();
    }

    public static boolean isDeveloper(UserContext userContext) {
        return userContext.getAuthorities().stream().filter(role ->
                Role.DEVELOPER.authority().equals(role.getAuthority())
        ).findFirst().isPresent();
    }

    public static boolean isAdmin(UserContext userContext) {
        return userContext.getAuthorities().stream().filter(role ->
                Role.ADMIN.authority().equals(role.getAuthority())
        ).findFirst().isPresent();
    }

    public static boolean isDeveloperOrAdmin(UserContext userContext) {
        return isDeveloper(userContext) || isAdmin(userContext);
    }
}
