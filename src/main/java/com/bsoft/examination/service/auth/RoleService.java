package com.bsoft.examination.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.examination.common.HttpStatus;
import com.bsoft.examination.common.Result;
import com.bsoft.examination.common.auth.UserInfo;
import com.bsoft.examination.domain.auth.Role;
import com.bsoft.examination.mapper.role.RoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年04月21日 13:20:00
 */
@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private UserInfo userInfo;

    public Result getRoleList(Map<String, Object> params) {
        Result<List<Role>> result = new Result<>();
        try {
            String pageNum = (String) params.get("page");
            String pageSize = (String) params.get("pageSize");
            Page<Role> page = new Page<Role>(1, 10);

            if (StringUtils.isNoneBlank(pageNum, pageNum)) {
                page.setPages(Long.parseLong(pageNum));
                page.setSize(Long.parseLong(pageSize));
            }
            IPage<Role> roleIPage = roleMapper.getRoleList(page);
            long total = roleIPage.getTotal();
            List<Role> roleList = roleIPage.getRecords();
            result.setCode(HttpStatus.OK);
            result.setMessage("查询成功");
            result.setData(roleList);
            result.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("服务错误");
        }
        return result;
    }

    public Result save(Role role, String op) {
        Result<Role> result = new Result<>();

        try {
            if ("create".equals(op)) {
                QueryWrapper<Role> query = new QueryWrapper<>();
                query.eq("roleName", role.getRoleName());
                int num = roleMapper.selectCount(query);
                if (num > 0) {
                    result.setCode(HttpStatus.NO_CONTENT);
                    result.setMessage("角色名已存在");
                    return result;
                }
                role.setCreateUser(userInfo.getUsername());
                roleMapper.save(role);
                result.setCode(HttpStatus.OK);
                result.setMessage("保存成功");
                result.setData(role);
            } else {
                roleMapper.updateById(role);
                result.setCode(HttpStatus.OK);
                result.setMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("服务错误");
        }
        return result;
    }

    public Result getRolesByUser(int userId) {
        Result<List<Role>> result = new Result<>();

        try {
            List<Role> roleList = roleMapper.getRolesByUser(userId);
            result.setData(roleList);
            result.setCode(HttpStatus.OK);
            result.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("服务错误");
        }
        return result;
    }

    public Result delete(int roleId) {
        Result result = new Result();

        try {
            roleMapper.deleteById(roleId);
            result.setCode(HttpStatus.OK);
            result.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("服务错误");
        }
        return result;
    }

    public Result saveResource(Role role) {
        Result result = new Result();

        try {
            if (role.getRoleId() != null) {
                roleMapper.deleteResource(role.getRoleId());
                if (role.getResource() != null && role.getResource().size() > 0) {
                    roleMapper.saveResource(role);
                }
                result.setCode(HttpStatus.OK);
                result.setMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
            result.setMessage("服务错误");
        }
        return result;
    }
}
