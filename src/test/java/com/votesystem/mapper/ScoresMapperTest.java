package com.votesystem.mapper;

import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.Scores;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ScoresMapperTest {

    @Autowired
    ScoresMapper scoresMapper;

    @Test
    void insertPlayerMatchFinalScore() {
        Scores scores = new Scores();
        scores.setPlayerId(149);
        scores.setMatchpkId(1);

//        算出评委成绩
        JudgeScores judgeScores = new JudgeScores();
        judgeScores.setPlayerId(149);
        judgeScores.setMatchpkId(1);
        double score = scoresMapper.findFinalJudgeScore(judgeScores);

        int finalScore = scoresMapper.insertPlayerMatchFinalScore(scores);
        System.out.println("finalScore = " + finalScore);
    }

    @Test
    void findFinalScore() {
    }

    @Test
    void selectFinalScoreInfo() {
        List<FinalScoreInfoVO> scoreInfoVOS = scoresMapper.selectFinalScoreInfo(null);
        System.out.println("scoreInfoVOS = " + scoreInfoVOS);
    }
}