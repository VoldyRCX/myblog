package com.voldy.myblog.vo;

import com.voldy.myblog.domain.Catalog;

import java.io.Serializable;

/**
 * TODO
 * 分类的VO对象
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/8
 **/
//TODO 序列化的作用？
public class CatalogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Catalog catalog;

    public CatalogVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

}