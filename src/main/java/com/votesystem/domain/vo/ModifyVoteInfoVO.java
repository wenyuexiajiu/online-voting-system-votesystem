package com.votesystem.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.votesystem.domain.Vote;
import lombok.Data;

/**
 * @author Ricardo
 */
@Data
public class ModifyVoteInfoVO {
    /**
     * 自增主键
     */
    private Integer voteId;

    /**
     * 参赛选手外键
     */
    private Integer playerId;

    /**
     * 比赛对战id外键
     */
    private Integer matchpkId;

    /**
     * 得票数目
     */
    private Integer voteNumbers;

    /**
     * 投票人的id值
     */
    private Integer audiId;
    /**
     * 选手的用户名
     */
    private String username;
    /**
     * 对手的id
     */
    private Integer playerOtherId;
}
