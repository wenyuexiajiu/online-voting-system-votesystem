package com.votesystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.votesystem.common.Result;
import com.votesystem.domain.Matchpk;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.FinalScoreInfoVO;
import com.votesystem.service.MatchpkService;
import com.votesystem.service.ScoresService;
import com.votesystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ricardo
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private MatchpkService matchpkService;
    @Autowired
    private ScoresService scoresService;

    @RequestMapping("/userManager/{pageNum}")
    public String userManager01(Model model,
                                @PathVariable(value = "pageNum") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Users> usersList = usersService.findUsers(null);
        PageInfo<Users> pageInfo = new PageInfo<Users>(usersList);
        model.addAttribute("pageInfo", pageInfo);
        return "userManager";
    }

    @PostMapping("/updateUsersInfo")
    @ResponseBody
    public Result updateUsersInfo(Users user) {
        return usersService.updateUsers(user);
    }

    /**
     * 比赛管理
     *
     * @return
     */
    @RequestMapping("/arrangeMatch")
    public String arrangeMatch(Model model,
                               @RequestParam(value = "matchId", required = false) Integer matchId) {
//        所有选手的信息
        List<Users> singerList = matchpkService.findSingerByUserScale(new Users());
//        已匹配成功的信息
        Matchpk matchpk = new Matchpk();
        matchpk.setMatchId(matchId);
        List<Matchpk> singerMatchInfo = matchpkService.findSingerMatchInfo(matchpk);
//        比赛场次id
        List<Integer> matchIdList = matchpkService.findMatchId();

        model.addAttribute("matchIdList", matchIdList);
        model.addAttribute("matchedList", singerMatchInfo);
        model.addAttribute("singerList", singerList);
        return "matchManager/arrangeMatch";
    }

    /**
     * 管理员手动编排比赛选手匹配
     *
     * @return
     */
    @RequestMapping("/addMatchInfo")
    @ResponseBody
    public Result addMatchInfo(Matchpk matchpk) {
        return matchpkService.addMatchInfo(matchpk);
    }

    /**
     * 管理员开启、关闭、暂停比赛投票通达
     *
     * @return
     */
    @RequestMapping("/controlMatchStatus")
    @ResponseBody
    public Result startMatch(Matchpk matchpk) {
        return matchpkService.modifyMatchStatus(matchpk);
    }

    /**
     * 排行榜页面映射
     *
     * @return
     */
    @RequestMapping("/rankings")
    public String rankings(Model model,
                           @RequestParam(value = "matchId", required = false) Integer matchId) {
        //        比赛场次id集合
        List<Integer> matchIdList = matchpkService.findMatchId();
        //比赛排行榜id
        List<FinalScoreInfoVO> finalScoreInfoVOS = scoresService.showFinalScoreInfo(matchId);
        model.addAttribute("finalScoreList", finalScoreInfoVOS);

        model.addAttribute("matchIdList", matchIdList);
        return "/matchManager/rankings";
    }

    /**
     * todo 实现比赛统计的方法，还差如何查询出排行榜数据
     *
     * @param matchId 比赛场次id
     * @return
     */
    @RequestMapping("/analyzeScore")
    @ResponseBody
    public Result analyzeScore(Integer matchId) {
        return scoresService.calcAllScoresByMatchId(matchId);
    }
}
