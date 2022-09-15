package com.votesystem.domain.vo;

import lombok.Data;

/**
 * @author DengJin
 * @ClassName SingerVoteInfoVO
 * @date 2022-07-10 23:51
 * @Description: 实时投票对象
 * @Version:1.0
 */
@Data
public class SingerVoteInfoVO {
    /**
     * 选手A名称
     */
    private String singerNameA;
    /**
     * 选手A的歌曲作品
     */
    private String singerMusicA;
    /**
     * 选手B名称
     */
    private String singerNameB;
    /**
     * 选手B的歌曲作品
     */
    private String singerMusicB;
    /**
     * 包含选手a总票数、选手票数以及所占百分比
     */
    private VotingPercentageVO votingPercentageVOA;
    /**
     * 包含选手b总票数、选手票数以及所占百分比
     */
    private VotingPercentageVO votingPercentageVOB;

    /**
     * 选手aid
     */
    private Integer singerIdA;
    /**
     * 选手bid
     */
    private Integer singerIdB;
    /**
     * 对战场次id
     */
    private Integer matchId;
    /**
     * 选手1的头像
     */
    private String userImg01;
    /**
     * 选手2的头像
     */
    private String userImg02;
}
