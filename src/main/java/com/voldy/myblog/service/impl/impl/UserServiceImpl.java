package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.User;
import com.voldy.myblog.repository.UserRepository;
import com.voldy.myblog.service.impl.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * TODO
 * 用户服务接口实现
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    /**
     * 模糊查询
     * @param nickname
     * @param pageable
     * @return
     */
    @Override
    public Page<User> listUsersByNicknameLike(String nickname, Pageable pageable) {
        nickname = "%" + nickname + "%";
        return userRepository.findByNicknameLike(nickname, pageable);
    }

    @Override
    public List<User> listUsersByUserNames(Collection<String> userNames) {
        return userRepository.findByUsernameIn(userNames);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        //解决remember me 空指针异常
        if(user == null){
            throw new UsernameNotFoundException("Username not exist");
        }
        return user;
    }



}
