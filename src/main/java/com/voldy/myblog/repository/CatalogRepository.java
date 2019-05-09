package com.voldy.myblog.repository;

import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/8
 **/
@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Long> {
    /**
     * 根据用户查询分类列表
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户以及分类名称查询查询分类列表
     * @param user
     * @param Name
     * @return
     */
    List<Catalog> findByUserAndName(User user, String Name);
}
