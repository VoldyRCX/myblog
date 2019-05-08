package com.voldy.myblog.controller;

import com.voldy.myblog.domain.Blog;
import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.Vote;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.service.impl.BlogService;
import com.voldy.myblog.service.impl.CatalogService;
import com.voldy.myblog.service.impl.UserService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.utils.OwnerJudge;
import com.voldy.myblog.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(UserspaceController.class);

    @Resource
    private UserService userService;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BlogService blogService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private OwnerJudge ownerJudge;

    /**
     * 用户主页
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public ModelAndView userSpace(@PathVariable("username") String username) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        ModelAndView mav = new ModelAndView("redirect:/u/" + username + "/blogs");
        mav.addObject("user", user);
        return mav;
    }

    /**
     * 获取个人设置页面
     * @param username
     * @return
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username) {
        ModelAndView mav = new ModelAndView("userspace/profile");
        User user = (User) userService.loadUserByUsername(username);
        mav.addObject("user", user);
        return mav;
    }

    /**
     * 保存修改的用户信息
     * @param username
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) throws  UnsupportedEncodingException {
        User savingUser = userService.getUserById(user.getId());
        savingUser.setEmail(user.getEmail());
        savingUser.setNickname(user.getNickname());

        String rawPassword = savingUser.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            savingUser.setPassword(encodedPassword);
        }
        userService.updateUser(savingUser);
        logger.info("UpdateUser: " + savingUser.getUsername());
        return "redirect:/u/" + URLEncoder.encode(username, "UTF-8") + "/profile";
    }


    /**
     * 博客查询
     * @param username
     * @param order
     * @param catalogId
     * @param keyword
     * @param async
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/{username}/blogs")
    public ModelAndView listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value="order",required=false,defaultValue="new") String order,
                                   @RequestParam(value="catalog",required=false ) Long catalogId,
                                   @RequestParam(value="keyword",required=false,defaultValue="") String keyword,
                                   @RequestParam(value = "async",required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
                                   ) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        Page<Blog> page = null;

        if(catalogId != null && catalogId > 0){ //分类查询
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
        }else if(order.equals("hot")){ //最热查询
            Sort sort = new Sort(Sort.Direction.DESC, "readings", "comments", "votes");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
        }else if(order.equals("new")){ //最新查询
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleLike(user, keyword, pageable);
        }
        List<Blog> list = new ArrayList<>();
        if(page != null){
            list = page.getContent();//当前页面数据列表
        }


        ModelAndView mav = new ModelAndView(async ? "userspace/u :: #mainContainerReplace" : "userspace/u");
        //TODO BlogVO
        mav.addObject("user", user);
        mav.addObject("order", order);
        mav.addObject("catalogId", catalogId);
        mav.addObject("page", page);
        mav.addObject("keyword", keyword);
        mav.addObject("blogList", list);

        return mav;
    }


    /**
     * 具体博客（按照ID)
     * @param username
     * @param id
     * @return
     */
    @GetMapping("/{username}/blogs/{id}")
    public ModelAndView getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        System.out.println(id);
//        User user = (User) userDetailsService.loadUserByUsername(username);
        User principal = null;
        Blog blog = blogService.getBlogById(id);
        System.out.println(blog);
        //blog.set(blog.getContent().replace("\n","<br>"));
        /*blog.setUser(userService.getUserById(user.getId()));*/
        //TODO ???

        //每次阅读 阅读量加1
        blogService.readingIncrease(id);

        //判断是否为博客拥有着
        boolean isBlogOwner = ownerJudge.isOwner(username);
        principal = ownerJudge.getOwnerUser();

        // 判断操作用户的点赞情况
        List<Vote> votes = blog.getVoteList();
        Vote currentVote = null; // 当前用户的点赞情况

        if (principal !=null) {
            for (Vote vote : votes) {
                vote.getUser().getUsername().equals(principal.getUsername());
                currentVote = vote;
                break;
            }
        }


        ModelAndView mav = new ModelAndView("userspace/blog");

        mav.addObject("isBlogOwner", isBlogOwner);
        mav.addObject("blog", blog);
        mav.addObject("currentVote", currentVote);


        return mav;
    }


    /**
     * 获取新增博客 界面
     * @param username
     * @return
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username) {
        User user = (User) userService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        ModelAndView mav = new ModelAndView("userspace/blogedit");
        mav.addObject("blog", new Blog(null, null, null));
        mav.addObject("catalogs", catalogs);
        return mav;
    }

    /**
     * 编辑博客界面
     * @param username
     * @param id
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        User user = (User) userService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        ModelAndView mav = new ModelAndView("userspace/blogedit");
        mav.addObject("blog", blogService.getBlogById(id));
        mav.addObject("catalogs", catalogs);
        //TODO 添加文件服务器

        return mav;
    }


    /**
     * 修改博客的POST方法接口
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<ResponseVO> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        if (blog.getCatalog().getId() == null) {//必须创建分类
            return ResponseEntity.ok().body(new ResponseVO(false, "未选择分类"));
        }
        try {
            if (blog.getId() != null) {//修改
                Blog originalBlog = blogService.getBlogById(blog.getId());
                originalBlog.setTitle(blog.getTitle());
                originalBlog.setContent(blog.getContent());
                originalBlog.setHtmlContent(blog.getHtmlContent());
                originalBlog.setSummary(blog.getSummary());
                originalBlog.setCatalog(blog.getCatalog());
                originalBlog.setTags(blog.getTags());
                blogService.saveBlog(originalBlog);
            } else {//新增
                User user = (User) userService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }

        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", redirectUrl));
    }

    /**
     * 删除博客
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}") //delete方法
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<ResponseVO> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {

        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", redirectUrl));
    }

    /**
     * 获取头像
     * @param username
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username) {
        User user = (User) userService.loadUserByUsername(username);
        ModelAndView mav = new ModelAndView("userspace/avatar");
        mav.addObject("user", user);
        return mav;
    }

    /**
     * 修改图像
     * TODO:存储在本地或者建立文件服务器
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<ResponseVO> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getIcon();

        User originalUser = userService.getUserById(user.getId());
        originalUser.setIcon(avatarUrl);
        userService.updateUser(originalUser);

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", avatarUrl));
    }
}
