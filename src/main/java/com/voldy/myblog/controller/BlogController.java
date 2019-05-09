package com.voldy.myblog.controller;

import com.voldy.myblog.domain.User;
import com.voldy.myblog.domain.es.EsBlog;
import com.voldy.myblog.service.impl.BlogService;
import com.voldy.myblog.service.impl.EsBlogService;
import com.voldy.myblog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 * blog控制器
 * @author 任程显--Voldy--
 * @version 0.0.2
 * @since 2019/5/9
 **/
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Resource
    private EsBlogService esBlogService;
    @GetMapping
    public ModelAndView listEsBlogs(
            @RequestParam(value="order",required=false,defaultValue="new") String order,
            @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
            @RequestParam(value="async",required=false) boolean async,
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize
            ) {

        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询
                Sort sort = new Sort(Sort.Direction.DESC,"readSize","commentSize","voteSize","createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) { // 最新查询
                Sort sort = new Sort(Sort.Direction.DESC,"createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }

            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }
        if(page != null){
            list = page.getContent();	// 当前所在页面数据列表
        }

        ModelAndView mav = new ModelAndView(async ? "/index :: #mainContainerRepleace" : "/index");


        mav.addObject("order", order);
        mav.addObject("keyword", keyword);
        mav.addObject("page", page);
        mav.addObject("blogList", list);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            mav.addObject("newest", newest);
            List<EsBlog> hottest = esBlogService.listTop5HotestEsBlogs();
            mav.addObject("hottest", hottest);
            List<TagVO> tags = esBlogService.listTop30Tags();
            mav.addObject("tags", tags);
            List<User> users = esBlogService.listTop12Users();
            mav.addObject("users", users);
        }

        return mav;
    }

    @GetMapping("/newest")
    public ModelAndView listNewestEsBlogs() {
        List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
        ModelAndView mav = new ModelAndView("newest");
        mav.addObject("newest", newest);
        return mav;
    }

    @GetMapping("/hotest")
    public ModelAndView listHotestEsBlogs() {
        List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
        ModelAndView mav = new ModelAndView("hotest");
        mav.addObject("newest", hotest);
        return mav;
    }


}