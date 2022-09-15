package com.votesystem.mapper;

import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.vo.AddJudgeScoresVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JudgeScoresMapperTest {

    @Autowired
    JudgeScoresMapper mapper;

    @Test
    void insertPlayerScore() {
        AddJudgeScoresVo scores = new AddJudgeScoresVo();
        scores.setPlayerId(149);
        scores.setJudgeScoresScore(93.7);
        scores.setMatchpkId(1);
        scores.setJudgerId(190);
        int insertPlayerScore = mapper.insertPlayerScore(scores);
        System.out.println("insertPlayerScore = " + insertPlayerScore);
    }
}