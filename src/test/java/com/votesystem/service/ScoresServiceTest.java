package com.votesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.common.Result;
import com.votesystem.domain.Scores;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;
import com.votesystem.mapper.ScoresMapper;
import com.votesystem.mapper.VoteMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
class ScoresServiceTest {

    @Autowired
    ScoresService scoresService;
    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private ScoresMapper scoresMapper;

    @Test
    void calcFinalScore() {
        Scores scores = new Scores();
        scores.setPlayerId(146);
        scores.setMatchpkId(2);
        Result result = scoresService.calcFinalScore(scores);
        System.out.println("result = " + result);
    }

    @Test
    void isAllEnd() {
        boolean allEnd = scoresService.isAllEnd(1);
        System.out.println("allEnd = " + allEnd);
    }

    @Test
    void calc() {
        Vote vote = new Vote();
        vote.setPlayerId(97);
        vote.setMatchpkId(2);
        VotingPercentageVO percentageVO = scoresService.calcVotePercentageSelf(vote);
        System.out.println("percentageVO = " + percentageVO);
    }

    @Test
    void findPlayerId() {
        Integer matchId = 1;
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getMatchpkId, matchId);
        List<Vote> matchpkList = voteMapper.selectList(wrapper);
        System.out.println("matchpkList = " + matchpkList);
    }

    @Test
    void calcAllScoresByMatchId() {
        Result result = scoresService.calcAllScoresByMatchId(1);
        System.out.println("result = " + result);
    }

    @Test
    void isAnalyzedScore() {
        Integer matchId = 1;
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getMatchpkId, matchId);
        List<Vote> matchpkList = voteMapper.selectList(wrapper);
        List<Integer> playerIdList = matchpkList.stream().map(Vote::getPlayerId).distinct().collect(Collectors.toList());
        System.out.println("playerIdList = " + playerIdList);
        List<Scores> scores = scoresMapper.selectFinalScoresByPlayerId(playerIdList, 1);
        System.out.println("scores = " + scores);
    }
    @Test
    void showFinalScoreInfo(){
        List<FinalScoreInfoVO> finalScoreInfoVOS = scoresService.showFinalScoreInfo(2);
        System.out.println("finalScoreInfoVOS = " + finalScoreInfoVOS);
    }
}