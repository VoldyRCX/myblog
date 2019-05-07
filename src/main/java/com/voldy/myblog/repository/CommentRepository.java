package com.voldy.myblog.repository;

import com.voldy.myblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * CommentRepository 接口
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
