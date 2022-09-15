package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ricardo
 * @TableName vote
 */
@TableName(value = "vote")
@Data
public class Vote implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(exist = false)
    private Integer audiId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}