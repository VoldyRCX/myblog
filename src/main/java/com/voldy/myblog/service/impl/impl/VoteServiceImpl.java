package com.voldy.myblog.service.impl.impl;

import com.voldy.myblog.domain.Vote;
import com.voldy.myblog.repository.VoteRepository;
import com.voldy.myblog.service.impl.VoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
@Service
public class VoteServiceImpl implements VoteService {

    @Resource
    private VoteRepository voteRepository;

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElse(null);
    }

    @Override
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }
}
