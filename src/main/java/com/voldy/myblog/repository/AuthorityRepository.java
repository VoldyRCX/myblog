package com.voldy.myblog.repository;

import com.voldy.myblog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * 权限仓库
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
