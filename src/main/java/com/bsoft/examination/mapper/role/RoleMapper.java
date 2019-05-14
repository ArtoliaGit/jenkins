package com.bsoft.examination.mapper.role;

import com.bsoft.examination.domain.auth.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 保存角色
     * @param role
     * @return
     */
    int save(Role role);

    /**
     * 获取资源列表
     * @param roleId
     * @return
     */
    List<String> getResource(@Param("role_id") int roleId);

    /**
     * 根据用户获取角色
     * @param userId
     * @return
     */
    List<Role> getRolesByUser(@Param("user_id") int userId);

    /**
     * 获取用户角色列表
     * @return
     */
    IPage<Role> getRoleList(Page page);

    /**
     * 删除菜单
     */
    int deleteResource(int roleId);

    /**
     * 保存菜单
     * @param role
     * @return
     */
    int saveResource(Role role);
}
