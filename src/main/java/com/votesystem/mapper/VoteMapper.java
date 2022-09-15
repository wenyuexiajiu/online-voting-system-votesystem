package com.votesystem.mapper;

import com.votesystem.domain.Vote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.votesystem.domain.VoteAudi;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【vote】的数据库操作Mapper
 * @createDate 2022-07-05 21:28:25
 * @Entity com.votesystem.domain.Vote
 */
@Mapper
public interface VoteMapper extends BaseMapper<Vote> {
    /**
     * 根据比赛id,选手id，观众id进行查找
     *
     * @param vote matchpk_id,player_id,audi_id
     * @return
     */
    VoteAudi selectVoteInfo(Vote vote);

    /**
     * 更新票数
     *
     * @param vote
     * @return
     */
    int updateVoteNum(Vote vote);

    /**
     * 修改票数的同时记录数据
     * 添加中间表数据
     *
     * @param vote
     * @return
     */
    int insertVoteAudiInfo(Vote vote);

    /**
     * 添加选手到投票表中
     *
     * @param vote
     * @return
     */
    int insertVoteInfo(Vote vote);

    /**
     * 根据比赛场次来获取两个选手的信息
     *
     * @param vote 比赛场次
     * @return
     */
    List<Vote> selectPlayerVoteByMatch(Vote vote);

    /**
     * 查找某个参赛选手的总票数
     *
     * @param vote 比赛场次id、选手id
     * @return
     */
    @Select("select count(audi_id) as votedNum from vote_audi where matchpk_id=#{matchpkId} and player_id=#{playerId}")
    VotingPercentageVO selectVotePercent(Vote vote);

    /**
     * 查询某场已经开启的比赛观众投票的总票数
     *
     * @param vote 两个选手的id、以及比赛场次
     * @return
     */
    @Select("select count(audi_id)  from vote_audi where matchpk_id=#{matchId} and player_id=#{singerIdA} OR player_id=#{singerIdB}")
    Integer selectVoteTotalByAudience(SingerVoteInfoVO vote);


    /**
     * 根据参赛选手的id以及比赛场次查找投票信息表
     *
     * @param vote 参赛选手id、比赛场次id
     * @return
     */

    @Select("select * from vote where matchpk_id=#{matchpkId} and player_id=#{playerId}")
    Vote findVoteInfoByPlayerId(Vote vote);

    /**
     * 查询是否已经投票
     *
     * @param modifyVoteInfoVO
     * @return
     */
    @Select("select player_id,vote_aud_id,audi_id,matchpk_id from vote_audi where matchpk_id=#{matchpkId} and audi_id=#{audiId} and(player_id=#{playerId} or player_id=#{playerOtherId})")
    List<VoteAudi> selectVoteAudiInfo(ModifyVoteInfoVO modifyVoteInfoVO);
}




