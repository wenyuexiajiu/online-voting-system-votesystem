package com.votesystem.mapper;

import com.votesystem.domain.JudgeScores;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.votesystem.domain.vo.AddJudgeScoresVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ricardo
 * @description 针对表【judge_scores】的数据库操作Mapper
 * @createDate 2022-07-05 19:12:55
 * @Entity com.votesystem.domain.JudgeScores
 */
@Mapper
public interface JudgeScoresMapper extends BaseMapper<JudgeScores> {

    /**
     * 为参赛选手打分
     *
     * @param judgeScores 评委id、选手id、分数、比赛场次
     * @return
     */
    int insertPlayerScore(AddJudgeScoresVo judgeScores);
    /**
     * 查询满足指定条件的评委评分信息
     *
     * @param judgeScores 选手编号、评委编号、比赛场次id
     * @return
     */
    JudgeScores selectPlayerJudgeScores(AddJudgeScoresVo judgeScores);
}




