package com.voldy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO
 * blog控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping
    public String listBlog(@RequestParam(value="order",required=false,defaultValue="new") String order,
                            @RequestParam(value="keyword",required=false,defaultValue = "") String keyword) {
        System.out.println("order:" +order + ";keyword:" +keyword);
        return "redirect:/index?order="+order+"&keyword="+keyword;
    }
}
