package com.votesystem.controller;

import com.votesystem.common.Result;
import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.vo.AddJudgeScoresVo;
import com.votesystem.service.JudgeScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ricardo
 */
@Controller
@RequestMapping("/judge")
public class JudgeScoresController {

    @Autowired
    private JudgeScoresService judgeScoresService;

    /**
     * 评委进行评分
     *
     * @param adJudgeScore
     * @return
     */
    @RequestMapping("/addJudgeScore")
    @ResponseBody
    public Result addScore(AddJudgeScoresVo adJudgeScore) {
        return judgeScoresService.addScorePlayer(adJudgeScore);
    }
}
