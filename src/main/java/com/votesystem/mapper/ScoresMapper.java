package com.votesystem.mapper;

import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Scores;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【scores】的数据库操作Mapper
 * @createDate 2022-07-05 19:13:15
 * @Entity com.votesystem.domain.Scores
 */
@Mapper
public interface ScoresMapper extends BaseMapper<Scores> {

    /**
     * 添加选手的最终分数及相关信息
     *
     * @param scores
     * @return int
     */
    int insertPlayerMatchFinalScore(Scores scores);

    /**
     * 计算某一个选手的最终评委分数
     *
     * @param judgeScores 参赛选手id、比赛场次
     * @return 计算出的平均成绩
     */
    Double findFinalJudgeScore(JudgeScores judgeScores);

    /**
     * 查询比赛结束后小组的信息
     *
     * @param matchpk
     * @return
     */
    @Select("select player_a, player_music_a,player_b,player_music_b, matchPK_status,match_id from matchpk where matchPK_status=0 and match_id=#{matchId}")
    Matchpk selectMatchInfoByMatchStatusAndMatchId(Matchpk matchpk);

    /**
     * 查询某轮比赛中开启或站厅状态下的对战情况
     *
     * @param matchId 比赛场次ID
     * @return
     */
    @Select("SELECT matchpk.id,matchpk.match_id,matchpk.player_a,matchpk.player_music_a,matchpk.player_b,matchpk.player_music_b,matchpk.matchPK_status FROM matchpk WHERE ( matchpk.matchPK_status =2 OR matchpk.matchPK_status = 1 ) AND matchpk.match_id = #{matchId}")
    List<Matchpk> selectMatchersInfoByMatchStatus(Integer matchId);

    /**
     * 根据比赛ID或其中一名选手的id来查询另外一位同学的id
     *
     * @param vote
     * @return
     */
    @Select("SELECT * FROM matchpk WHERE (matchpk.player_a = #{playerId} OR matchpk.player_b = #{playerId}) AND matchpk.match_id = #{matchpkId}")
    Matchpk selectPlayerIdByGroup(Vote vote);

    /**
     * 根据选手ID以及比赛场次在最终得分表中查找 是否存在数据
     *
     * @param playerIdList
     * @param matchId
     * @return
     */
    List<Scores> selectFinalScoresByPlayerId(@Param("playerIdList") List<Integer> playerIdList, @Param("matchId") Integer matchId);

    /**
     * 根据比赛场次id来查找已经结束比赛的选手分数信息
     *
     * @param matchId 比赛场次id
     * @return
     */
    List<FinalScoreInfoVO> selectFinalScoreInfo(@Param("matchId") Integer matchId);

}




