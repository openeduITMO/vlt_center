package com.spring.boot.vlt.security.endpoint;

import com.spring.boot.vlt.common.ErrorCode;
import com.spring.boot.vlt.common.ErrorResponse;
import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.token.AccessJwtToken;
import com.spring.boot.vlt.mvc.model.token.JwtToken;
import com.spring.boot.vlt.mvc.model.token.JwtTokenFactory;
import com.spring.boot.vlt.mvc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserRegisterEndPoint {
    @Autowired
    UserService userService;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserRegisterEndPoint(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    ResponseEntity<JwtToken> register(@RequestBody User user) throws ServletException {
        String login = user.getLogin();
        String password = user.getPassword();
        if (StringUtils.isBlank(login) || StringUtils.isBlank(password))
            throw new IllegalArgumentException("Username or Password is blank: " + user.toString());

        Optional<User> userByLogin = userService.getUserByLogin(login);
        if (userByLogin.isPresent())
            return ErrorResponse.of("login already occupied", ErrorCode.USER_EXIST_IN_DARABASE, HttpStatus.UNAUTHORIZED);

        user.setPassword(encoder.encode(password));

        if (!userService.saveUser(user)||!userService.setRoleForUser(userByLogin.get().getId(), Role.STUDENT))
            return ErrorResponse.of("user not updatePropertyFile", ErrorCode.USER_NOT_SAVE, HttpStatus.UNAUTHORIZED);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.STUDENT.authority()));
        UserContext userContext = UserContext.create(user.getLogin(), authorities);

        AccessJwtToken accessJwtToken = jwtTokenFactory.createAccessJwtToken(userContext);
        return new ResponseEntity<>(accessJwtToken, HttpStatus.OK);
    }

}
