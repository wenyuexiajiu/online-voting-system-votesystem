package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Users;
import com.votesystem.domain.Vote;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;
import com.votesystem.exception.CustomersException;
import com.votesystem.mapper.UsersMapper;
import com.votesystem.service.MatchpkService;
import com.votesystem.mapper.MatchpkMapper;
import com.votesystem.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ricardo
 * @description 针对表【matchpk】的数据库操作Service实现
 * @createDate 2022-07-06 10:17:16
 */
@Service
@Transactional(rollbackFor = {CustomersException.class, Exception.class})
public class MatchpkServiceImpl extends ServiceImpl<MatchpkMapper, Matchpk>
        implements MatchpkService {
    @Autowired
    private MatchpkMapper matchpkMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private VoteService voteService;

    /**
     * 查询某个比赛场次的所有对战选手信息
     *
     * @param matchpk
     * @return
     */
    @Override
    public List<Matchpk> findSingerMatchInfo(Matchpk matchpk) {
        List<Matchpk> matchpks = matchpkMapper.selectMatched(matchpk);
        List<Matchpk> matchpkListBySorted = matchpks.stream().sorted(Comparator.comparing(Matchpk::getMatchId)).collect(Collectors.toList());
        return matchpkListBySorted;
    }

    /**
     * 查找还未参加比赛的歌手
     *
     * @param user
     * @return
     */
    @Override
    public List<Users> findSingerByUserScale(Users user) {
        user.setUserScale("singer");
        user.setMatchpkId(1);
        List<Users> users = matchpkMapper.selectSingerByUserScale(user);
        return users;
    }

    /**
     * 添加选手匹配的相关信息
     *
     * @param matchpk
     * @return
     */
    @Override
    public Result addMatchInfo(Matchpk matchpk) {
//        默认不开启比赛
        matchpk.setMatchpkStatus(0);
//        可在此增加查询歌手比赛的逻辑
        LambdaQueryWrapper<Users> wrapperA = new LambdaQueryWrapper<>();
        wrapperA.eq(Users::getUserId, matchpk.getPlayerA());
        Users playerA = usersMapper.selectOne(wrapperA);

//        找到选手b的歌曲
        LambdaQueryWrapper<Users> wrapperB = new LambdaQueryWrapper<>();
        wrapperB.eq(Users::getUserId, matchpk.getPlayerB());
        Users playerB = usersMapper.selectOne(wrapperB);

//        匹配成功后，添加选手的信息到投票表中，默认投票数为0
        Vote vote = new Vote();
//        注入比赛场次编号
        vote.setMatchpkId(matchpk.getMatchId());
        vote.setPlayerId(matchpk.getPlayerA());

//        将选手A的投票信息添加到数据库中
        boolean isInsertPlayerA = voteService.addPlayerIntoVote(vote);
        vote.setPlayerId(matchpk.getPlayerB());
//          将选手B的投票信息添加到数据库中
        boolean isInsertPlayerB = voteService.addPlayerIntoVote(vote);


//        添加歌曲
        matchpk.setPlayerMusicA(playerA.getPlayerMusic());
        matchpk.setPlayerMusicB(playerB.getPlayerMusic());
        int i = matchpkMapper.insertMatchInfo(matchpk);

//        判断新增是否否成功
        if (i != 1 || !isInsertPlayerA || !isInsertPlayerB) {
            throw new CustomersException(500, "匹配错误，请稍后再试~");
        }
        return Result.success(200, "匹配成功", matchpk);
    }

    /**
     * 更新比赛信息
     *
     * @param matchpk
     * @return
     */
    @Override
    public Result modifyMatchStatus(Matchpk matchpk) {
//        判断开启比赛前是否有其他比赛开启了
        if (matchpk.getMatchpkStatus() == 1) {
            Matchpk temp = matchpkMapper.selectMatchInfoByMatchStatusAndMatchId(matchpk);
            if (temp != null) {
//                存在比赛已经开启了，直接抛出异常
                throw new CustomersException(300, temp.getPlayerA() + "号 与 " + temp.getPlayerB() + "号 正在进行第 " + temp.getMatchId() + " 轮PK");
            }
        }
        int updateMatchStatus = matchpkMapper.updateMatchStatus(matchpk);
        if (updateMatchStatus != 1) {
            throw new CustomersException(500, "更新失败");
        }
        return Result.success(200, "更新成功", matchpk);
    }

    /**
     * 查找所有比赛场次id
     *
     * @return
     */
    @Override
    public List<Integer> findMatchId() {
        List<Integer> integerList = matchpkMapper.selectMatchIds();
        //        排序操作
        List<Integer> usersList = integerList.stream().sorted().collect(Collectors.toList());
        return usersList;
    }

    /**
     * 根据比赛场次查找已经开启的比赛
     *
     * @param matchId 比赛场次id
     * @return 返回选手票数对象
     */
    @Override
    public Result<SingerVoteInfoVO> showSingerVotingInfo(Integer matchId) {
        SingerVoteInfoVO singerVoteInfoVO = new SingerVoteInfoVO();

        Matchpk matchpk = new Matchpk();
        matchpk.setMatchId(matchId);
        //某个比赛场次下的比赛投票
        Matchpk singersMatchInfo = matchpkMapper.selectMatchInfoByMatchStatusAndMatchId(matchpk);

//        判断该比赛场次是否没有对应的比赛
        if (singersMatchInfo == null) {
            throw new CustomersException(250, "该场次比赛没有对应的投票，请选择其他比赛场次~");
        }

        //根据选手a的id查找对应信息
        LambdaQueryWrapper<Users> wrapperA = new LambdaQueryWrapper<>();
        wrapperA.eq(Users::getUserId, singersMatchInfo.getPlayerA());
        Users playerA = usersMapper.selectOne(wrapperA);

//        找到选手b的歌曲
        LambdaQueryWrapper<Users> wrapperB = new LambdaQueryWrapper<>();
        wrapperB.eq(Users::getUserId, singersMatchInfo.getPlayerB());
        Users playerB = usersMapper.selectOne(wrapperB);

        Vote vote = new Vote();
        vote.setMatchpkId(matchId);
        //获取选手A的票数百分比以及票数

        vote.setPlayerId(playerA.getUserId());
        VotingPercentageVO percentageVOA = voteService.calculateVotePercentage(vote).getData();

        //获取选手B的票数百分比以及票数
        vote.setPlayerId(playerB.getUserId());
        VotingPercentageVO percentageVOB = voteService.calculateVotePercentage(vote).getData();
        //注入选手的名称
        //比赛场次id
        singerVoteInfoVO.setMatchId(matchId);
        //选手A的id
        singerVoteInfoVO.setSingerIdA(playerA.getUserId());
        singerVoteInfoVO.setSingerNameA(playerA.getUserRealName());
        singerVoteInfoVO.setSingerMusicA(playerA.getPlayerMusic());

        //选手B的id
        singerVoteInfoVO.setSingerIdB(playerB.getUserId());
        singerVoteInfoVO.setSingerNameB(playerB.getUserRealName());
        singerVoteInfoVO.setSingerMusicB(playerB.getPlayerMusic());

        //注入选手的总票数以及百分占比
        singerVoteInfoVO.setVotingPercentageVOA(percentageVOA);
        singerVoteInfoVO.setVotingPercentageVOB(percentageVOB);

        return Result.success(200, "查询成功", singerVoteInfoVO);
    }
}




