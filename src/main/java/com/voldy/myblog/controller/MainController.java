package com.voldy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO
 * 主页控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Controller
public class MainController {

    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("login-error")
    public ModelAndView loginError(){
        ModelAndView mav = new ModelAndView("/login");
        mav.addObject("loginError", true);
        return mav;
    }
}
