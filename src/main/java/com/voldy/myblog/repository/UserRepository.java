package com.voldy.myblog.repository;

import com.voldy.myblog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * TODO
 * User Repository接口
 * @author 任程显
 * @version 0.0.1
 * @since 2019/5/4
 **/
//继承spring data JPA接口
public interface UserRepository extends CrudRepository<User,Long> {

}
