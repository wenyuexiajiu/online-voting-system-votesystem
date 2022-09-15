package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Users;
import com.votesystem.domain.Vote;
import com.votesystem.domain.VoteAudi;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.domain.vo.VotingPercentageVO;
import com.votesystem.exception.CustomersException;
import com.votesystem.mapper.MatchpkMapper;
import com.votesystem.mapper.ScoresMapper;
import com.votesystem.mapper.UsersMapper;
import com.votesystem.service.VoteService;
import com.votesystem.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【vote】的数据库操作Service实现
 * @createDate 2022-07-05 21:28:25
 */
@Service
@Transactional(rollbackFor = {CustomersException.class, Exception.class})
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote>
        implements VoteService {

    private VoteMapper voteMapper;
    private MatchpkMapper matchpkMapper;
    private UsersMapper usersMapper;
    @Autowired
    private ScoresMapper scoresMapper;

    @Autowired
    public VoteServiceImpl(VoteMapper voteMapper, MatchpkMapper matchpkMapper, UsersMapper usersMapper) {
        this.voteMapper = voteMapper;
        this.matchpkMapper = matchpkMapper;
        this.usersMapper = usersMapper;
    }

    /**
     * 观众进行投票票数
     *
     * @param modifyVoteInfoVO 选手id、对手的id 、观众的用户名username、比赛场次matchId
     * @return
     */
    @Override
    public Result modifyVoteNum(ModifyVoteInfoVO modifyVoteInfoVO) {
//        根据用户名查找用户的id
        Users users = usersMapper.selectUsersInfoByUsersAccount(modifyVoteInfoVO.getUsername());
        modifyVoteInfoVO.setAudiId(users.getUserId());
        if (isVoted(modifyVoteInfoVO)) {
            throw new CustomersException(500, "您只有一次投票机会");
        }
//        修改票数
        Vote vote = new Vote();
        vote.setPlayerId(modifyVoteInfoVO.getPlayerId());
        vote.setMatchpkId(modifyVoteInfoVO.getMatchpkId());
        vote.setAudiId(modifyVoteInfoVO.getAudiId());
        int updateVoteNum = voteMapper.updateVoteNum(vote);
        if (updateVoteNum != 1) {
            throw new CustomersException(401, "投票失败，请稍后再试~");
        }


//        将观众投票记录在中间表中 vote_audi
        int insertVoteAudiInfo = voteMapper.insertVoteAudiInfo(vote);
        if (insertVoteAudiInfo != 1) {
            throw new CustomersException(401, "投票失败，请稍后再试~");
        }
        return Result.success(200, "投票成功", null);
    }

    /**
     * 添加选手到投票表中
     *
     * @param vote 选手id 、比赛场次id
     * @return boolean ,成功为true、失败为false
     */
    @Override
    public boolean addPlayerIntoVote(Vote vote) {
//        设置初始值票数为0
        vote.setVoteNumbers(0);
        int insertVoteInfo = voteMapper.insertVoteInfo(vote);
        if (insertVoteInfo != 1) {
            return false;
        }
        return true;
    }

    /**
     * 判断该观众是否已经投票了
     *
     * @param modifyVoteInfoVO 观众id、选手id、对战场次id
     * @return boolean
     */
    @Override
    public boolean isVoted(ModifyVoteInfoVO modifyVoteInfoVO) {

        List<VoteAudi> voteAudi = voteMapper.selectVoteAudiInfo(modifyVoteInfoVO);
        if (voteAudi.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据比赛场次找到对应的选手
     *
     * @param vote 比赛场次
     * @return voteList
     */
    @Override
    public List<Vote> findPlayerVoteInfo(Vote vote) {
        List<Vote> voteList = voteMapper.selectPlayerVoteByMatch(vote);
        return voteList;
    }

    /**
     * 计算票数占比
     * 仅供业务逻辑代码使用
     *
     * @param vote 选手id、比赛场次
     * @return 返回票数占比对象
     */
    @Override
    public Result<VotingPercentageVO> calculateVotePercentage(Vote vote) {
        Matchpk matchpk = new Matchpk();
        matchpk.setMatchId(vote.getMatchpkId());

        //获取当前比赛场次的两位选手信息
        Matchpk matchpk1 = matchpkMapper.selectMatchInfoByMatchStatusAndMatchId(matchpk);

        SingerVoteInfoVO singerVoteInfoVO = new SingerVoteInfoVO();
        String userImg01 = usersMapper.selectOne((new LambdaQueryWrapper<Users>()).eq(Users::getUserId, matchpk1.getPlayerA())).getUserImg();
        String userImg02 = usersMapper.selectOne((new LambdaQueryWrapper<Users>()).eq(Users::getUserId, matchpk1.getPlayerB())).getUserImg();

        singerVoteInfoVO.setUserImg01(userImg01);
        singerVoteInfoVO.setUserImg02(userImg02);
        singerVoteInfoVO.setSingerIdA(matchpk1.getPlayerA());
        singerVoteInfoVO.setSingerIdB(matchpk1.getPlayerB());
        singerVoteInfoVO.setMatchId(matchpk1.getMatchId());

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

        return Result.success(200, "查询成功！", votePercent);
    }

    /**
     * 根据参赛选手以及比赛场次进行查找投票表的id
     *
     * @param vote 参赛选手id、比赛场次ID
     * @return
     */
    @Override
    public Vote findVoteIdByPlayerIdAndMatchId(Vote vote) {
        return voteMapper.findVoteInfoByPlayerId(vote);
    }
}




