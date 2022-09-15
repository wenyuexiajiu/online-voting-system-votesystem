package com.votesystem.mapper;

import com.votesystem.domain.Vote;
import com.votesystem.domain.VoteAudi;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoteMapperTest {

    @Autowired
    private VoteMapper voteMapper;

    @Test
    void selectVoteInfo() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        vote.setPlayerId(178);
        vote.setAudiId(90);
        VoteAudi voteAudi = voteMapper.selectVoteInfo(vote);
        System.out.println("voteAudi = " + voteAudi);
    }

    @Test
    void updateVoteNum() {

    }

    @Test
    void insertVoteAudiInfo() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        vote.setPlayerId(178);
        vote.setAudiId(90);
        int i = voteMapper.insertVoteAudiInfo(vote);
        System.out.println("i = " + i);
    }

    @Test
    void insertVoteInfo() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        vote.setPlayerId(178);
        int i = voteMapper.insertVoteInfo(vote);
        System.out.println("i = " + i);
    }

    @Test
    void selectPlayerVoteByMatch() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        List<Vote> votes = voteMapper.selectPlayerVoteByMatch(vote);
        System.out.println("votes = " + votes);
    }

    @Test
    void findVoteInfoByPlayerId() {
        Vote vote = new Vote();
        vote.setMatchpkId(1);
        vote.setPlayerId(149);
        Vote voteInfoByPlayerId = voteMapper.findVoteInfoByPlayerId(vote);
        System.out.println("voteInfoByPlayerId = " + voteInfoByPlayerId);
    }

    @Test
    void selectVoteAudiInfo() {
//        ModifyVoteInfoVO modifyVoteInfoVO = new ModifyVoteInfoVO();
//        modifyVoteInfoVO.setMatchpkId();
    }
}