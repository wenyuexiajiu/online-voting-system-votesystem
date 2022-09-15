package com.votesystem.service;

import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Scores;
import com.baomidou.mybatisplus.extension.service.IService;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【scores】的数据库操作Service
 * @createDate 2022-07-05 19:13:16
 */
public interface ScoresService extends IService<Scores> {
    /**
     * 计算出参赛选手的最后总得分
     *
     * @param scores 参赛选手id、比赛场次id
     * @return
     */
    Result calcFinalScore(Scores scores);

    /**
     * 判断在统计成绩前是否该场次的比赛都已经结束
     *
     * @param matchID 比赛场次ID
     * @return
     */
    boolean isAllEnd(Integer matchID);

    /**
     * 用于统计分析计算单个选手的票数百分比
     *
     * @param vote
     * @return
     */
    VotingPercentageVO calcVotePercentageSelf(Vote vote);

    /**
     * 拿到比赛场次id，从而获取该场次下所有参赛选手的数据
     *
     * @param matchId
     * @return
     */
    Result calcAllScoresByMatchId(Integer matchId);

    /**
     * 判断是否已经统计分析过成绩了
     *
     * @param playerList
     * @param matchId
     * @return
     */
    boolean isAnalyzedScore(List<Integer> playerList, Integer matchId);

    /**
     * 通过比赛id来查找指定选手的最终成绩
     *
     * @param matchId
     * @return
     */
    List<FinalScoreInfoVO> showFinalScoreInfo(Integer matchId);

}
