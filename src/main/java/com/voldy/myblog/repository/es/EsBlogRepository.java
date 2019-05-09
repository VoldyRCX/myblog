package com.voldy.myblog.repository.es;

import com.voldy.myblog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * Esblog接口
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/9
 **/
@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String> {

    /**
     * 模糊查询(去重)
     * @param title
     * @param Summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

    /**
     * 根据Blog 的Id 查询EsBlog
     * @param blogId
     * @return
     */
    EsBlog findByBlogId(Long blogId);

}
