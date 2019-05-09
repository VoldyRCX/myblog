package com.voldy.myblog.dto;

import com.voldy.myblog.vo.MenuVO;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 用户管理页面数据
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
public class AdminDTO {
    List<MenuVO> list = new ArrayList<>();

    public AdminDTO() {
    }
    
    public AdminDTO(List<MenuVO> list) {
        this.list = list;
    }

    public List<MenuVO> getList() {
        return list;
    }

    public void setList(List<MenuVO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "list=" + list +
                '}';
    }
}
