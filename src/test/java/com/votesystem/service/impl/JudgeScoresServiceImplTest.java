package com.votesystem.service.impl;

import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.vo.AddJudgeScoresVo;
import com.votesystem.service.JudgeScoresService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JudgeScoresServiceImplTest {
    @Autowired
    JudgeScoresService judgeScoresService;

    @Test
    void addScorePlayer() {
        AddJudgeScoresVo scores = new AddJudgeScoresVo();
        scores.setPlayerId(149);
        scores.setJudgeScoresScore(89.3);
        scores.setMatchpkId(1);
        scores.setJudgerId(187);
        judgeScoresService.addScorePlayer(scores);
        System.out.println("judgeScoresService = " + judgeScoresService);
    }
}