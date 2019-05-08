package com.voldy.myblog.controller;

import com.voldy.myblog.domain.Blog;
import com.voldy.myblog.domain.Comment;
import com.voldy.myblog.domain.User;
import com.voldy.myblog.service.impl.BlogService;
import com.voldy.myblog.service.impl.CommentService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.utils.OwnerJudge;
import com.voldy.myblog.vo.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * TODO
 * CommentController评论控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;

    @Resource
    private OwnerJudge ownerJudge;

    /**
     * 获取评论列表
     * @param blogId
     * @return
     */
    @GetMapping
    public ModelAndView listComments(@RequestParam(value="blogId",required=true) Long blogId) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> commentList = blog.getCommentList();

        // 判断操作用户是否是评论的所有者
        String commentOwner = ownerJudge.getOwnerUsername();


        ModelAndView mav = new ModelAndView("/userspace/blog :: #mainContainerRepleace");
        mav.addObject("commentOwner", commentOwner);
        mav.addObject("commentList", commentList);

        return mav;
    }

    /**
     * 发表评论
     * @param blogId
     * @param commentContent
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<ResponseVO> createComment(Long blogId, String commentContent) {

        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", null));
    }


    /**
     * 删除评论
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO> deleteBlog(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        User user = commentService.getCommentById(id).getUser();

        // 判断操作用户是否是博客的所有者
        isOwner = ownerJudge.isOwner(user);


        if (!isOwner) {
            return ResponseEntity.ok().body(new ResponseVO(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(blogId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "处理成功", null));
    }
}

