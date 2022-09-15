package com.votesystem.service;

import com.votesystem.common.Result;
import com.votesystem.domain.JudgeScores;
import com.baomidou.mybatisplus.extension.service.IService;
import com.votesystem.domain.vo.AddJudgeScoresVo;

/**
 * @author Ricardo
 * @description 针对表【judge_scores】的数据库操作Service
 * @createDate 2022-07-05 19:12:55
 */
public interface JudgeScoresService extends IService<JudgeScores> {
    /**
     * 评委为选手进行打分
     *
     * @param judgeScores 评委id、选手id、分数、比赛场次
     * @return Result
     */
    Result addScorePlayer(AddJudgeScoresVo judgeScores);

    /**
     * 判断该评委是否已经评分
     *
     * @param judgeScores 评委id、比赛场次、选手id
     * @return
     */
    boolean isJudgeScores(AddJudgeScoresVo judgeScores);
}
