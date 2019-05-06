package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.Authority;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
public interface AuthorityService {
    /**
     * 根据id获取 Authority
     * @param id
     * @return
     */
    Authority getAuthorityById(Long id);
}
