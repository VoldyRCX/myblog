package com.voldy.myblog.controller;

import com.voldy.myblog.domain.User;
import com.voldy.myblog.service.impl.BlogService;
import com.voldy.myblog.service.impl.VoteService;
import com.voldy.myblog.utils.ConstraintViolationExceptionHandler;
import com.voldy.myblog.utils.OwnerJudge;
import com.voldy.myblog.vo.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

/**
 * TODO
 * 点赞控制器
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Controller
@RequestMapping("/votes")
public class VoteController {

    @Resource
    private BlogService blogService;

    @Resource
    private VoteService voteService;

    @Resource
    private OwnerJudge ownerJudge;
    /**
     * 发表点赞
     * @param blogId
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<ResponseVO> createVote(Long blogId) {

        try {
            blogService.createVote(blogId);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "点赞成功", null));
    }

    /**
     * 删除点赞
     * @return
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<ResponseVO> delete(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        User user = voteService.getVoteById(id).getUser();

        // 判断操作用户是否是点赞的所有者
        isOwner = ownerJudge.isOwner(user);
        /*if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
*/
        if (!isOwner) {
            return ResponseEntity.ok().body(new ResponseVO(false, "没有操作权限"));
        }

        try {
            blogService.removeVote(blogId, id);
            voteService.removeVote(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new ResponseVO(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseVO(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new ResponseVO(true, "取消点赞成功", null));
    }
}

