package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.Authority;
import com.voldy.myblog.repository.AuthorityRepository;
import com.voldy.myblog.service.impl.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 * Authority服务接口实现
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityRepository authorityRepository;
    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.getOne(id);
    }
}
