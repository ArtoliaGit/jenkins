package com.bsoft.examination.mapper.user;

import com.bsoft.examination.domain.auth.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author Artolia Pendragon
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 保存用户
     * @param user
     * @return
     */
    Integer save(User user);

    /**
     * 获取用户列表
     * @param page
     * @return
     */
    IPage<User> getUserList(Page page, @Param("params") Map<String, Object> map);


}
