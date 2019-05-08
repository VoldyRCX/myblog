package com.voldy.myblog.utils;

import com.voldy.myblog.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 身份判断
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Component
public class OwnerJudge {

    /**
     * 根据username判断
     * @param username
     * @return
     */
    public Boolean isOwner(String username){
        Boolean isOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && username.equals(principal.getUsername())) {
                isOwner = true;
            }
        }
        return isOwner;
    }

    /**
     * 根据user判断
     * @param user
     * @return
     */
    public Boolean isOwner(User user){
        Boolean isOwner = false;
        return isOwner(user.getUsername());
    }

    /**
     * 获取当前用户对象
     * @return
     */
    public User getOwnerUser(){
        User principal = null;
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return principal;
    }
    /**
     * 获取当前用户名
     * @return
     */
    public String getOwnerUsername(){
        String ownerUsername = "";
        User user = getOwnerUser();
        if(user != null){
            ownerUsername = user.getUsername();
        }
        return ownerUsername;
    }


}
