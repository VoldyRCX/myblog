package com.voldy.myblog.repository;

import com.voldy.myblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 * User Repository接口
 * @author 任程显
 * @version 0.0.2
 * @since 2019/5/5
 **/
//继承spring data JPA接口
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据姓名分页查询
     * @param nickname
     * @param pageable
     * @return
     */
    Page<User> findByNicknameLike(String nickname, Pageable pageable);

    /**
     * 根据用户账号查询
     * @param username
     * @return
     */
    User findByUsername(String username);
}
