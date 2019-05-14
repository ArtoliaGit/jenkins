package com.bsoft.examination.web.auth;

import com.bsoft.examination.domain.auth.Role;
import com.bsoft.examination.service.auth.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年04月21日 14:09:00
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoleList")
    public String getRoleList(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        Map<String, Object> params = new HashMap<>(8);
        params.put("page", page);
        params.put("pageSize", pageSize);
        return roleService.getRoleList(params).toJson();
    }

    @PostMapping("/save")
    public String save(@RequestBody Role role, HttpServletRequest request) {
        String op = request.getParameter("op");
        return roleService.save(role, op).toJson();
    }

    @GetMapping("/delete")
    public String delete(int roleId) {
        return roleService.delete(roleId).toJson();
    }

    @PostMapping("/saveResource")
    public String saveResource(@RequestBody Role role) {
        return roleService.saveResource(role).toJson();
    }
}
