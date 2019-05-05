package com.voldy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO
 * 用户主页控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Controller
@RequestMapping("/u")
public class UserspaceController {

    @GetMapping("/{username}")
    public ModelAndView userSpace(@PathVariable("username") String username) {
        ModelAndView mav = new ModelAndView("u");
        System.out.println("username" + username);
        return mav;
    }

    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value="order",required=false,defaultValue="new") String order,
                                   @RequestParam(value="category",required=false ) Long category,
                                   @RequestParam(value="keyword",required=false ) String keyword) {

        if (category != null) {

            System.out.print("category:" +category );
            System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?category="+category);
            return "/u";

        } else if (keyword != null && keyword.isEmpty() == false) {

            System.out.print("keyword:" +keyword );
            System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?keyword="+keyword);
            return "/u";
        }

        System.out.print("order:" +order);
        System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?order="+order);
        return "/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {

        System.out.print("blogId:" + id);
        return "/blog";
    }


    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {

        return "/blogedit";
    }
}
