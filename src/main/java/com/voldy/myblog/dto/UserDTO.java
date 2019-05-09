package com.voldy.myblog.dto;

import com.voldy.myblog.domain.User;

import javax.annotation.Resource;

/**
 * TODO
 * User控制器的传输对象
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
public class UserDTO {

    private User user;
    private Long authorityId;

    public UserDTO() {
    }

    public UserDTO(User user, Long authorityId) {
        this.user = user;
        this.authorityId = authorityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "user=" + user +
                ", authorityId=" + authorityId +
                '}';
    }
}
