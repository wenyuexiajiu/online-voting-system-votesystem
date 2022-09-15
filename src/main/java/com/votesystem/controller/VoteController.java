package com.votesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.common.Result;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.ModifyVoteInfoVO;
import com.votesystem.domain.vo.SingerVoteInfoVO;
import com.votesystem.service.MatchpkService;
import com.votesystem.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ricardo
 * 实时投票管理
 */
@Controller
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;
    @Autowired
    private MatchpkService matchpkService;


    /**
     * 保存比赛场次id
     *
     * @param matchId
     * @param model
     * @return
     */
    @RequestMapping("/votePage/{matchId}")
    public String votePage(@PathVariable("matchId") Integer matchId,
                           Model model) {
        model.addAttribute("matchId", matchId);
        return "votePage/votePage";
    }

    /**
     * 实时显示投票信息
     *
     * @param matchId
     * @return
     */
    @RequestMapping("/showVote")
    @ResponseBody
    public Result<SingerVoteInfoVO> showVote(Integer matchId) {
        Result<SingerVoteInfoVO> singerVoteInfoVOResult = matchpkService.showSingerVotingInfo(matchId);
        return singerVoteInfoVOResult;
    }

    /**
     * 观众投票操作
     *
     * @param voteInfoVO
     * @return result
     */
    @RequestMapping("/addVoteNum")
    @ResponseBody
    public Result addVoteNum(ModifyVoteInfoVO voteInfoVO) {
        Result result = voteService.modifyVoteNum(voteInfoVO);
        return result;
    }
}
