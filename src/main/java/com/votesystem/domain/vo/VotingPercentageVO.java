package com.votesystem.domain.vo;

import lombok.Data;

/**
 * 获取选手各自票数占比
 * @author Ricardo
 */
@Data
public class VotingPercentageVO {
    /**
     * 总票数
     */
    private Integer voteTotal;

    /**
     * 选手的投票数
     */
    private Integer votedNum;
    /**
     * 票数占比
     */
    private Double votingPercentage;
}
