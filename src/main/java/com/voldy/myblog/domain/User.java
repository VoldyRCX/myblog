package com.voldy.myblog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO
 * User 实体
 * @author 任程显
 * @version 0.0.1
 * @since 2019/5/4
 **/
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //根据数据库自增
    private Long id;
    private String name;
    private String email;

    protected User() { //需要protected的无参构造函数
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
