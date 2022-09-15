package com.votesystem.domain.vo;

import lombok.Data;

/**
 * @author DengJin
 * @ClassName FinalScoreInfoVO
 * @date 2022-07-11 20:41
 * @Description:
 * @Version:1.0
 */
@Data
public class FinalScoreInfoVO {
    /**
     * 选手姓名
     */
    private String playerName;
    /**
     * 选手id
     */
    private Integer playerId;
    /**
     * 竞赛作品
     */
    private String musicName;
    /**
     * 获得票数
     */
    private Integer voteNum;
    /**
     * 票数占比
     */
    private double votePercentage;
    /**
     * 评委分数
     */
    private double judgeScore;
    /**
     * 综合得分
     */
    private double finalScore;
    /**
     * 是否晋级
     */
    private Integer isPromotion;
    /**
     * 比赛场次
     */
    private Integer matchpkId;

}
