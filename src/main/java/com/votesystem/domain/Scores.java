package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName scores
 */
@TableName(value ="scores")
@Data
public class Scores implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer scoresId;

    /**
     * 参赛选手外键
     */
    private Integer playerId;

    /**
     * 比赛对战id
     */
    private Integer matchpkId;

    /**
     * 综合分数
     */
    private Double scoresFinal;

    /**
     * 投票得分外键id
     */
    private Integer voteId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}