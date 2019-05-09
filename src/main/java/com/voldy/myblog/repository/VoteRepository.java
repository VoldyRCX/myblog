package com.voldy.myblog.repository;

import com.voldy.myblog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/7
 **/
public interface VoteRepository extends JpaRepository<Vote,Long> {
}
