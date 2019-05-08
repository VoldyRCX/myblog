package com.voldy.myblog.controller;

import com.voldy.myblog.domain.Authority;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.dto.UserDTO;
import com.voldy.myblog.repository.UserRepository;
import com.voldy.myblog.service.impl.AuthorityService;
import com.voldy.myblog.service.impl.UserService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.vo.ResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * User 管理员对user控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/4
 **/
@RestController
@RequestMapping("/users")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AuthorityService authorityService;


    /**
     * 新建用户
     * @param user
     * @param authorityId
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseVO> create(User user, Long authorityId) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);

        if(user.getId() == null) {
            user.setEncodePassword(user.getPassword()); // 加密密码
        }else {
            // 判断密码是否做了变更
            User originalUser = userService.getUserById(user.getId());
            String rawPassword = originalUser.getPassword();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(user.getPassword());
            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
            if (!isMatch) {
                user.setEncodePassword(user.getPassword());
            }else {
                user.setPassword(user.getPassword());
            }
        }

        try {
            userService.registerUser(user);
        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", user));
    }

    /**
     * 查询所用用户
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="name",required=false,defaultValue="") String name
                            ) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNicknameLike(name, pageable);
        List<User> list = page.getContent();	// 当前所在页面数据列表
        ModelAndView mav = new ModelAndView(async ? "users/list :: #mainContainerRepleace" : "users/list");
        mav.addObject("page", page);
        mav.addObject("userList", list);
        return mav;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseVO> delete(@PathVariable("id") Long id) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new ResponseVO(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new ResponseVO(true, "处理成功"));
    }


    /**
     * 获取新建用户form表单
     * @return
     */
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("users/add");
        mav.addObject("user", new UserDTO());
        return mav;
    }

    /**
     * 获取修改用户界面
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("users/edit");
        User user = userService.getUserById(id);
        //Long authId = authorityService.getAuthorityById(id).getId();
        mav.addObject("user", user);
        return mav;
    }


}
