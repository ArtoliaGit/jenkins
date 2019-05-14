package com.bsoft.examination.web.auth;

import com.bsoft.examination.domain.auth.User;
import com.bsoft.examination.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Artolia Pendragon
 */
@RestController
@RequestMapping("authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws AuthenticationException {
        return authService.login(user.getUsername(), user.getPassword()).toJson();
    }

}
