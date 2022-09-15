package com.votesystem.service.impl;

import com.votesystem.common.Result;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import com.votesystem.service.UsersService;
import com.votesystem.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoteServiceImplTest {

    @Autowired
    private VoteService voteService;

    @Test
    void modifyVoteNum() {
        ModifyVoteInfoVO vote = new ModifyVoteInfoVO();
        vote.setMatchpkId(1);
        vote.setPlayerId(149);
        vote.setUsername("admin");
        Result result = voteService.modifyVoteNum(vote);
        System.out.println("result = " + result);
    }

    @Test
    void addPlayerIntoVote() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        vote.setPlayerId(149);
        vote.setAudiId(91);
        boolean result = voteService.addPlayerIntoVote(vote);
        System.out.println("result = " + result);
    }


    @Test
    void calculateVotePercentage() {
        Vote vote = new Vote();
        vote.setPlayerId(178);
        vote.setMatchpkId(1);
        //Result result = voteService.calculateVotePercentage(vote);
        //System.out.println("result = " + result);
    }
}