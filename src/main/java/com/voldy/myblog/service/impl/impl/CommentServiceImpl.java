package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.Comment;
import com.voldy.myblog.repository.CommentRepository;
import com.voldy.myblog.service.impl.CommentService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * TODO
 * CommentService实现
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;


    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}
