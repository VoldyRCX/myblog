package com.voldy.myblog.controller;

import com.voldy.myblog.dto.AdminDTO;
import com.voldy.myblog.service.impl.UserService;
import com.voldy.myblog.vo.MenuVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private UserService userService;
    /**
     * TODO 获取后台管理主页面
     * @return
     */
    @GetMapping
    public ModelAndView listUsers() {
        List<MenuVO> list = new ArrayList<>();
        list.add(new MenuVO("用户管理", "/users"));
        list.add(new MenuVO("角色管理", "/roles"));
        list.add(new MenuVO("博客管理", "/blog"));
        list.add(new MenuVO("评论管理", "/commits"));
        AdminDTO adminDTO = new AdminDTO(list);
        ModelAndView mav = new ModelAndView("admins/index");
        mav.addObject("list", adminDTO);
        return mav;
    }


}
