package com.voldy.myblog.controller;

import com.voldy.myblog.domain.Authority;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.dto.UserDTO;
import com.voldy.myblog.repository.UserRepository;
import com.voldy.myblog.service.impl.AuthorityService;
import com.voldy.myblog.service.impl.UserService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.vo.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
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
    private UserService userService;
    @Resource
    private AuthorityService authorityService;


    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseVO> createOrUpdateUser(UserDTO userDTO){

        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(userDTO.getAuthorityId()));
        userDTO.getUser().setAuthorities(authorities);

        try{
            userService.updateUser(userDTO.getUser());
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", userDTO));
    }
    /**
     * 查询所有用户
     * @return
     */
    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("users/list");
        mav.addObject("userList", userService.listUsers());
        return mav;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id){
        userService.removeUser(id);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 修改用户信息
     * @param id
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        ModelAndView mav = new ModelAndView("form.html.bak");
        if(user != null) {
            mav.addObject("user", user);
        }
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("users/add");
        mav.addObject("user", new UserDTO());
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("users/edit");
        User user = userService.getUserById(id);
        Long authId = authorityService.getAuthorityById(id).getId();
        mav.addObject("user", new UserDTO(user,authId));
        return mav;
    }


}
