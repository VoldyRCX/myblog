package com.voldy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO
 * 管理员后台管理控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * TODO 获取后台管理主页面
     * @return
     */
    @GetMapping
    public ModelAndView listUsers() {

        return new ModelAndView("admins/index");
    }


}
