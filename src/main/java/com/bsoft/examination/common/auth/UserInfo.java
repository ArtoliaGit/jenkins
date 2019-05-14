package com.bsoft.examination.common.auth;

import com.bsoft.examination.domain.auth.Role;
import com.bsoft.examination.domain.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年03月24日 17:18:00
 */
@Component
public class UserInfo {

    public User getUser() {
        final Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();
        return user;
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public List<Role> getRoles() {
        return getUser().getRoles();
    }

}
