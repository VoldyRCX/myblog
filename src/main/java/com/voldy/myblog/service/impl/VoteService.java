package com.voldy.myblog.service.impl;

import com.voldy.myblog.domain.Vote;

/**
 * VoteService 接口
 * TODO
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
public interface VoteService {

    /**
     * 根据id获取 Vote
     * @param id
     * @return
     */
    Vote getVoteById(Long id);

    /**
     * 删除Like
     * @param id
     * @return
     */
    void removeVote(Long id);
}
