package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName vote_audi
 */
@TableName(value = "vote_audi")
@Data
public class VoteAudi implements Serializable {
    /**
     * 自增id
     */
    @TableId
    private Integer voteAudId;

    /**
     * a
     * 选手id
     */
    private Integer playerId;

    /**
     * 投票人id
     */
    private Integer audiId;
    /**
     * 比赛对战id
     */
    private Integer matchpkId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}