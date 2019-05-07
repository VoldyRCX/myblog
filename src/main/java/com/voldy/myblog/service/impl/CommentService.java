package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.Comment;

/**
 * TODO
 * CommentService接口
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/

public interface CommentService {

    /**
     * 根据id获取 Comment
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 删除评论
     * @param id
     * @return
     */
    void removeComment(Long id);
}

