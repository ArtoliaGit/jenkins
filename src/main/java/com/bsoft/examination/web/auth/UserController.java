package com.bsoft.examination.web.auth;

import com.bsoft.examination.domain.auth.User;
import com.bsoft.examination.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description 用户操作
 * @createTime 2019年03月24日 17:12:00
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserInfo")
    public String getUserInfo() {
        return userService.getUserInfo().toJson();
    }

    @GetMapping("/getUserList")
    public String getUserList(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String username = request.getParameter("username");
        Map<String, Object> params = new HashMap<>(8);
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("username", username);
        return userService.getUserList(params).toJson();
    }

    @PostMapping("/save")
    public String save(@RequestBody User user, HttpServletRequest request) {
        String op = request.getParameter("op");
        return userService.save(user, op).toJson();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int userId) {
        return userService.delete(userId).toJson();
    }
}
