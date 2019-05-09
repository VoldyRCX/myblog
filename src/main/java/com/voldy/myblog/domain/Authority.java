package com.voldy.myblog.domain;



import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * TODO
 * 权限实体
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
@Entity
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id; // 用户的唯一标识

    @NotEmpty(message = "权限信息不能为空")
    @Column(nullable = false) // 映射为字段，值不能为空
    private String name;

    protected Authority() {
    }

    public Authority(@NotEmpty(message = "权限信息不能为空") String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
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

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
