package com.spring.boot.vlt.security.auth.basic;

import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.UserRole;
import com.spring.boot.vlt.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    public BasicAuthenticationProvider(final BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional.of(authentication).orElseThrow(() -> new IllegalArgumentException("No authentication data provided"));

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

//        User user = userService.getUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<UserRole> roles = new ArrayList<>();
        roles.add(new UserRole(1L, Role.ADMIN));
        User user = new User(1L, "admin", "password", roles);
        if (!user.getLogin().equals(username)) throw new IllegalArgumentException("No authentication data provided");

//        if (!encoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//        }
        if (!user.getPassword().equals(password)) throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");

        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getLogin(), authorities);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
