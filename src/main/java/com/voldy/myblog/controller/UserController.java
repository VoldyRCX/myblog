package com.voldy.myblog.controller;

import com.voldy.myblog.domain.User;
import com.voldy.myblog.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * User 控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/4
 **/
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserRepository userRepository;

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("users/list");
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        mav.addObject("userList", list);
        return mav;
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable("id") Long id){
        User user = userRepository.findById(id).orElse(null);
        ModelAndView mav = new ModelAndView("users/view");
        if(user != null){
            mav.addObject("user", user);
        }

        return mav;
    }

    /**
     * 获取创建用户表单页面
     * @return
     */
    @GetMapping("/form")
    public ModelAndView createForm(){
        ModelAndView mav = new ModelAndView("users/form");
        mav.addObject("user",new User(null,null,null));
        return mav;
    }

    /**
     * Post方法更新
     * @param user
     * @return
     */
    @PostMapping
    public ModelAndView saveOrUpdateForm(User user){
        userRepository.save(user);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 修改用户信息
     * @param id
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id){
        User user = userRepository.findById(id).orElse(null);
        ModelAndView mav = new ModelAndView("users/form");
        if(user != null) {
            mav.addObject("user", user);
        }
        return mav;
    }
}
