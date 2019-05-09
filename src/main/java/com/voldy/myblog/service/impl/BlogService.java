package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.Blog;
import com.voldy.myblog.domain.Catalog;
import com.voldy.myblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
public interface BlogService {
    /**
     * 保存博客
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void removeBlog(Long id);

    /**
     * 更新博客
     * @param blog
     * @return
     */
    Blog updateBlog(Blog blog);
    /**
     * 根据Id查询博客
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 根据博客名分页查询（最新）
     * @param user
     * @param title
     * @param pageable
     * @return
     */

    Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据博客名分页查询（最热）
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable);



    /**
     * 增加阅读量
     * @param id
     */
    void readingIncrease(Long id);

    /**
     * 创建评论
     * @param blogId
     * @param commentContent
     * @return
     */
    Blog createComment(Long blogId, String commentContent);

    /**
     * 删除评论
     * @param blogId
     * @param commentId
     */
    void removeComment(Long blogId, Long commentId);

    /**
     * 获得点赞
     * @param blogId
     * @return
     */
    Blog createVote(Long blogId);

    /**
     * 移除点赞
     * @param blogId
     * @param likeId
     */
    void removeVote(Long blogId, Long likeId);

    /**
     * 根据分类查询所有匹配的博客
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);
}
