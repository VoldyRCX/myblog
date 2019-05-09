package com.voldy.myblog.vo;

import java.io.Serializable;

/**
 * 菜单值对象
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public MenuVO(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}