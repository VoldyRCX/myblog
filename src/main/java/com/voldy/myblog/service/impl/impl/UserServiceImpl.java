package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.User;
import com.voldy.myblog.repository.UserRepository;
import com.voldy.myblog.service.impl.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * TODO
 * 用户服务接口实现
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;


    @Transactional
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUsersByNicknameLike(String nickname, Pageable pageable) {
        nickname = "%" + nickname + "%";
        return userRepository.findByNicknameLike(nickname, pageable);
    }

}
