package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.repository.CatalogRepository;
import com.voldy.myblog.service.impl.CatalogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 * CatalogService的实现类
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/8
 **/
@Service
public class CatalogServiceImpl implements CatalogService {

    @Resource
    private CatalogRepository catalogRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {

        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if(list !=null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findById(id).orElse(null);
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }

}
