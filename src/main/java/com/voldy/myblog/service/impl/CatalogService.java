package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.User;

import java.util.List;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/8
 **/
public interface CatalogService {

    /**
     * 保存分类
     * @param catalog
     * @return
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 移除分类标签
     * @param id
     */
    void removeCatalog(Long id);

    /**
     * 根据Id获取分类
     * @param id
     * @return
     */
    Catalog getCatalogById(Long id);

    /**
     * 列出所有分类
     * @param user
     * @return
     */
    List<Catalog> listCatalogs(User user);
}

