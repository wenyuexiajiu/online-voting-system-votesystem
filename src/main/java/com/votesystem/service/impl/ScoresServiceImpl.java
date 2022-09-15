package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.votesystem.common.Result;
import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Scores;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;
import com.votesystem.exception.CustomersException;
import com.votesystem.mapper.MatchpkMapper;
import com.votesystem.mapper.VoteMapper;
import com.votesystem.service.ScoresService;
import com.votesystem.mapper.ScoresMapper;
import com.votesystem.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ricardo
 * @description 针对表【scores】的数据库操作Service实现
 * @createDate 2022-07-05 19:13:15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScoresServiceImpl extends ServiceImpl<ScoresMapper, Scores>
        implements ScoresService {

    private ScoresMapper scoresMapper;
    private VoteService voteService;
    private VoteMapper voteMapper;

    @Autowired
    public ScoresServiceImpl(ScoresMapper scoresMapper, VoteService voteService, VoteMapper voteMapper) {
        this.scoresMapper = scoresMapper;
        this.voteMapper = voteMapper;
        this.voteService = voteService;
    }

    /**
     * 计算出单个参赛选手的最后总得分
     *
     * @param scores 参赛选手id、比赛场次id
     * @return
     */
    @Override
    public Result calcFinalScore(Scores scores) {
//        注入参赛选手id以及比赛场次id
        Vote vote = new Vote();
        vote.setPlayerId(scores.getPlayerId());
        vote.setMatchpkId(scores.getMatchpkId());

//        获取该选手票数占比
        double votingPercentage = calcVotePercentageSelf(vote).getVotingPercentage();
//        获取评委最终得分
        JudgeScores judgeScores = new JudgeScores();
        judgeScores.setPlayerId(scores.getPlayerId());
        judgeScores.setMatchpkId(scores.getMatchpkId());

//        多个评委所取的平均分
        Double finalJudgeScore = scoresMapper.findFinalJudgeScore(judgeScores);
        if (finalJudgeScore == null) {
            finalJudgeScore = 0.0;
        }
        //查找出投票的id
        Integer voteId = voteService.findVoteIdByPlayerIdAndMatchId(vote).getVoteId();
        scores.setVoteId(voteId);

//        计算综合得分
        double sumScore = votingPercentage + finalJudgeScore;
        scores.setScoresFinal(sumScore);

//        判断是否之前统计过，若存在该数据，则执行更新操作
        LambdaQueryWrapper<Scores> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Scores::getPlayerId, scores.getPlayerId()).eq(Scores::getMatchpkId, scores.getMatchpkId());
        Scores selectOne = scoresMapper.selectOne(wrapper);
        if (selectOne != null) {
//            执行更新操作
            LambdaUpdateWrapper<Scores> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Scores::getPlayerId, scores.getPlayerId()).eq(Scores::getMatchpkId, scores.getMatchpkId());
            Scores scores1 = new Scores();
            scores1.setScoresFinal(sumScore);
            int update = scoresMapper.update(scores1, wrapper);
            if (update != 1) {
                throw new CustomersException(500, "分数统计失败，请稍后再试~");
            }
        } else {
//        添加到数据库中保存
            int insertPlayerMatchFinalScore = scoresMapper.insertPlayerMatchFinalScore(scores);
//        若未插入成功，则抛出异常
            if (insertPlayerMatchFinalScore != 1) {
                throw new CustomersException(500, "分数统计失败，请稍后再试~");
            }
        }
        return Result.success(200, "分数统计成功", scores);
    }

    /**
     * 判断在统计成绩前是否该场次的比赛都已经结束
     *
     * @param matchId
     * @return 若存在未结束的对决，则返回true，否则返回false
     */
    @Override
    public boolean isAllEnd(Integer matchId) {
        List<Matchpk> list = scoresMapper.selectMatchersInfoByMatchStatus(matchId);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算票数百分比用于统计分析
     *
     * @param vote
     * @return
     */
    @Override
    public VotingPercentageVO calcVotePercentageSelf(Vote vote) {
        //获取当前比赛场次的两位选手信息
        Matchpk matchpk = scoresMapper.selectPlayerIdByGroup(vote);
        SingerVoteInfoVO singerVoteInfoVO = new SingerVoteInfoVO();
        singerVoteInfoVO.setSingerIdA(matchpk.getPlayerA());
        singerVoteInfoVO.setSingerIdB(matchpk.getPlayerB());
        singerVoteInfoVO.setMatchId(matchpk.getMatchId());

//        查询某个场次的比赛观众总投票数
        Integer voteTotal = voteMapper.selectVoteTotalByAudience(singerVoteInfoVO);

//        查找某个参赛选手的总票数
        VotingPercentageVO votePercent = voteMapper.selectVotePercent(vote);

//        选手的票数
        votePercent.setVoteTotal(voteTotal);
//        计算所占百分比
        double v = (votePercent.getVotedNum() / (double) votePercent.getVoteTotal()) * 100;
        double percent = Double.parseDouble(String.format("%.1f", v));
//        判断该数是否为NaN
        if (Double.isNaN(percent)) {
            percent = 0.0;
        }

//        乘100，作为评分的一部分
        votePercent.setVotingPercentage(percent);
        return votePercent;
    }

    /**
     * 拿到比赛场次id，从而获取该场次下所有参赛选手的数据
     *
     * @param matchId
     * @return
     */
    @Override
    public Result calcAllScoresByMatchId(Integer matchId) {
//        1、先判断该场次中是否存在还未结束的对决
        if (isAllEnd(matchId)) {
//          存在未结束对决，抛出异常
            throw new CustomersException(400, "该比赛中还存在未结束的对决，无法统计~");
        }
//      2、  先从数据库中找到所有该场次的选手信息
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getMatchpkId, matchId);
        List<Vote> matchpkList = voteMapper.selectList(wrapper);

        List<Integer> playerIdList = matchpkList.stream().map(Vote::getPlayerId).distinct().collect(Collectors.toList());
        //      4、  循环操作，调用计算总分的方法，并判断是否计算成功并添加到数据库中
        Scores scores = new Scores();
        scores.setMatchpkId(matchId);
        Result result;

        for (Integer playerId : playerIdList) {
            scores.setPlayerId(playerId);
            result = calcFinalScore(scores);
            if (result.getCode() != 200) {
                throw new CustomersException(500, "统计分数出现错误，请稍后再试~");
            }
        }
        return Result.success(200, "统计成功", null);
    }

    /**
     * 判断是否已经统计分析过成绩了
     *
     * @param playerList
     * @param matchId
     * @return
     */
    @Override
    @Deprecated
    public boolean isAnalyzedScore(List<Integer> playerList, Integer matchId) {
        List<Scores> scoresList = scoresMapper.selectFinalScoresByPlayerId(playerList, matchId);
        if (scoresList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过比赛id来查找指定选手的最终成绩
     *
     * @param matchId
     * @return
     */
    @Override
    public List<FinalScoreInfoVO> showFinalScoreInfo(Integer matchId) {
        //获取基本信息
        List<FinalScoreInfoVO> scoreInfoVOS = scoresMapper.selectFinalScoreInfo(matchId);
        JudgeScores judgeScores = new JudgeScores();
        Vote vote = new Vote();

        //获取其基本票数、以及评委分数、票数占比
        for (FinalScoreInfoVO score : scoreInfoVOS) {

            //        注入参赛选手id以及比赛场次id
            vote.setPlayerId(score.getPlayerId());
            vote.setMatchpkId(score.getMatchpkId());

            //显示该选手所得总票数
            VotingPercentageVO votePercent = voteMapper.selectVotePercent(vote);

//        获取该选手票数占比
            double votingPercentage = calcVotePercentageSelf(vote).getVotingPercentage();

            //获取该选手的评分
            judgeScores.setPlayerId(score.getPlayerId());
            judgeScores.setMatchpkId(score.getMatchpkId());

//        多个评委所取的平均分
            Double finalJudgeScore = scoresMapper.findFinalJudgeScore(judgeScores);
            if (finalJudgeScore == null) {
                finalJudgeScore = 0.0;
            }

            //注入到属性当中
            score.setVotePercentage(votingPercentage);
            score.setVoteNum(votePercent.getVotedNum());
            score.setJudgeScore(finalJudgeScore);
        }
        return scoreInfoVOS;
    }
}




