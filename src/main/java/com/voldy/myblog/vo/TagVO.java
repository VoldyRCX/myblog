package com.voldy.myblog.vo;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/9
 **/
public class TagVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Long count;

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
