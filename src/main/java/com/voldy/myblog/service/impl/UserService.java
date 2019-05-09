package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

/**
 * TODO
 * 用户服务接口
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
public interface UserService extends UserDetailsService {
    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 注册用户
     * @param user
     * @return
     */
    User registerUser(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    void removeUser(Long id);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 获取用户列表
     * @return List<User>
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     * @param nickname
     * @param pageable
     * @return
     */
    Page<User> listUsersByNicknameLike(String nickname, Pageable pageable);

    /**
     * 根据用户名返回用户列表
     * @param userNames
     * @return
     */
    public List<User> listUsersByUserNames(Collection<String> userNames);
}
