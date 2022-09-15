package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.votesystem.common.Result;
import com.votesystem.domain.JudgeScores;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.AddJudgeScoresVo;
import com.votesystem.exception.CustomersException;
import com.votesystem.mapper.UsersMapper;
import com.votesystem.service.JudgeScoresService;
import com.votesystem.mapper.JudgeScoresMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ricardo
 * @description 针对表【judge_scores】的数据库操作Service实现
 * @createDate 2022-07-05 19:12:55
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class JudgeScoresServiceImpl extends ServiceImpl<JudgeScoresMapper, JudgeScores>
        implements JudgeScoresService {

    private JudgeScoresMapper judgeScoresMapper;
    private UsersMapper usersMapper;

    @Autowired
    public JudgeScoresServiceImpl(JudgeScoresMapper judgeScoresMapper, UsersMapper usersMapper) {
        this.judgeScoresMapper = judgeScoresMapper;
        this.usersMapper = usersMapper;
    }

    /**
     * 评委为选手进行打分
     *
     * @param judgeScores 评委id、选手id、分数、比赛场次
     * @return Result
     */
    @Override
    public Result addScorePlayer(AddJudgeScoresVo judgeScores) {
        //      获取评委的id
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserAccount, judgeScores.getJudgeName());
        Users users = usersMapper.selectOne(wrapper);
//        注入对象中
        judgeScores.setJudgerId(users.getUserId());

//        评委打分前，需要先判断该评委是否已经打过分数了
        if (isJudgeScores(judgeScores)) {
//            说明已经打过分数了，则抛出异常
            throw new CustomersException(400, "您已经打过分数了，不能重复打分~");
        }

        int insertPlayerScore = judgeScoresMapper.insertPlayerScore(judgeScores);
        if (insertPlayerScore != 1) {
            throw new CustomersException(500, "评分出现错误，请稍后再试~");
        }
        return Result.success(200, "评分成功", null);
    }

    /**
     * 判断该评委是否已经评分
     *
     * @param judgeScores 评委id、比赛场次、选手id
     * @return
     */
    @Override
    public boolean isJudgeScores(AddJudgeScoresVo judgeScores) {
        JudgeScores judge = judgeScoresMapper.selectPlayerJudgeScores(judgeScores);
        if (judge == null) {
//            证明还没有打分
            return false;
        }
        return true;
    }
}




