package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.*;
import com.voldy.myblog.domain.es.EsBlog;
import com.voldy.myblog.repository.BlogRepository;
import com.voldy.myblog.service.impl.BlogService;
import com.voldy.myblog.service.impl.EsBlogService;
import com.voldy.myblog.service.impl.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogRepository blogRepository;
    @Resource
    private EsBlogService esBlogService;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        boolean isNew = (blog.getId() == null);
        EsBlog esBlog = null;

        Blog returnBlog = blogRepository.save(blog);

        if (isNew) {
            esBlog = new EsBlog(returnBlog);
        } else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(returnBlog);
        }

        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
        EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
        esBlogService.removeEsBlog(esblog.getId());
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog) {
        return saveBlog(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        return blog;
    }

    @Override
    public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        return blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        return blogRepository.findByUserAndTitleLike(user, title, pageable);
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.getOne(id);
        blog.setReadings(blog.getReadings()+1);
        this.saveBlog(blog);
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findById(blogId).orElse(null);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        if(originalBlog != null){
            originalBlog.addComment(comment);
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findById(blogId).orElse(null);
        if(originalBlog != null){
            originalBlog.removeComment(commentId);
        }

        this.saveBlog(originalBlog);
    }

    @Override
    public Blog createVote(Long blogId) {
        Blog originalBlog = blogRepository.findById(blogId).orElse(null);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = false;
        if(null != originalBlog){
            isExist = originalBlog.addvote(vote);
        }
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findById(blogId).orElse(null);
        if(null != originalBlog){
            originalBlog.removeVote(voteId);
        }
        this.saveBlog(originalBlog);
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findByCatalog(catalog, pageable);
    }


}
