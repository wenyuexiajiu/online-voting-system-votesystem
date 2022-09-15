package com.votesystem.service;

import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.votesystem.domain.Users;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【matchpk】的数据库操作Service
 * @createDate 2022-07-06 10:17:16
 */
public interface MatchpkService extends IService<Matchpk> {
    /**
     * 查询某个比赛场次的两个对战选手
     *
     * @param matchpk
     * @return
     */
    List<Matchpk> findSingerMatchInfo(Matchpk matchpk);

    /**
     * 查找歌手
     *
     * @param user
     * @return
     */
    List<Users> findSingerByUserScale(Users user);

    /**
     * 添加选手匹配的相关信息
     *
     * @param matchpk
     * @return
     */
    Result addMatchInfo(Matchpk matchpk);

    /**
     * 更新比赛信息
     *
     * @param matchpk
     * @return
     */
    Result modifyMatchStatus(Matchpk matchpk);

    /**
     * 查找所有比赛场次id
     *
     * @return
     */
    List<Integer> findMatchId();

    /**
     * 根据比赛场次查找已经开启的比赛选手投票信息
     *
     * @param matchId 包含实时投票页面的所有数据
     * @return
     */
    Result<SingerVoteInfoVO> showSingerVotingInfo(Integer matchId);
}
