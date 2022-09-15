package com.votesystem.service;

import com.votesystem.common.Result;
import com.votesystem.domain.Vote;
import com.baomidou.mybatisplus.extension.service.IService;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【vote】的数据库操作Service
 * @createDate 2022-07-05 21:28:25
 */
public interface VoteService extends IService<Vote> {
    /**
     * 观众进行投票
     *
     * @param modifyVoteInfoVO 选手id 、观众id、比赛场次
     * @return
     */
    Result modifyVoteNum(ModifyVoteInfoVO modifyVoteInfoVO);

    /**
     * 添加选手到投票表中
     *
     * @param vote 选手id 、比赛场次id
     * @return
     */
    boolean addPlayerIntoVote(Vote vote);

    /**
     * 判断该观众是否已经投票了
     *
     * @param modifyVoteInfoVO 观众id、选手id、对战场次id
     * @return
     */
    boolean isVoted(ModifyVoteInfoVO modifyVoteInfoVO);

    /**
     * 根据比赛场次找到对应的选手
     *
     * @param vote 比赛场次
     * @return
     */
    List<Vote> findPlayerVoteInfo(Vote vote);

    /**
     * 计算票数占比
     *
     * @param vote 选手id、比赛场次
     * @return 返回票数占比对象
     */
    Result<VotingPercentageVO> calculateVotePercentage(Vote vote);

    /**
     * 根据参赛选手以及比赛场次进行查找投标表的id
     *
     * @param vote
     * @return
     */
    Vote findVoteIdByPlayerIdAndMatchId(Vote vote);
}
