package com.voldy.myblog.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.voldy.myblog.domain.Authority;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.service.impl.AuthorityService;
import com.voldy.myblog.service.impl.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 主页控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Controller
public class MainController {

    private static final Long ROLE_USER_AUTHORITY_ID = 2L; //用户权限常量
    @Resource
    private UserService userService;
    @Resource
    private AuthorityService authorityService;
    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 获取登陆页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 获取登陆失败信息
     * @return
     */
    @GetMapping("login-error")
    public ModelAndView loginError(){
        ModelAndView mav = new ModelAndView("/login");
        mav.addObject("loginError", true);
        mav.addObject("errorMsg", "登陆失败，账号或者密码错误！");
        return mav;
    }

    /**
     * 获取注册页面
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册用户POST接口
     * TODO 注册成功后自动创建默认分类
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(User user){
        List<Authority> authorities = new ArrayList<>();
        //设置权限
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        //加密
        user.setEncodePassword(user.getPassword());
        //注册
        userService.registerUser(user);
        //TODO 成功注册后直接登陆
        return "redirect:/login";
    }
}
