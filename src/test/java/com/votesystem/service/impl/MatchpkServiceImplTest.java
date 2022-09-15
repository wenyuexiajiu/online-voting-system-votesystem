package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.mapper.UsersMapper;
import com.votesystem.service.MatchpkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatchpkServiceImplTest {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    MatchpkService matchpkService;

    @Test
    void findSingerMatchInfo() {
        Matchpk matchpk = new Matchpk();
        matchpk.setPlayerA(169);
        matchpk.setPlayerB(170);
        matchpk.setMatchpkStatus(0);
//        可在此增加查询歌手比赛的逻辑
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserId, matchpk.getPlayerA());
        Users playerA = usersMapper.selectOne(wrapper);
        System.out.println("playerA = " + playerA.getPlayerMusic());


//        找到选手b的歌曲
        LambdaQueryWrapper<Users> wrapperB = new LambdaQueryWrapper<>();
        wrapperB.eq(Users::getUserId, matchpk.getPlayerB());
        Users playerB = usersMapper.selectOne(wrapperB);
        System.out.println("playerB = " + playerB.getPlayerMusic());
    }

    @Test
    void findSingerByUserScale() {
    }

    @Test
    void addMatchIshowSingerVotingInfonfo() {
        Result<SingerVoteInfoVO> singerVoteInfoVOResult = matchpkService.showSingerVotingInfo(1);
        System.out.println("singerVoteInfoVOResult = " + singerVoteInfoVOResult.getData());
    }
}