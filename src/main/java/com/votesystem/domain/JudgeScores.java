package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @author Ricardo
 * @TableName judge_scores
 */
@TableName(value ="judge_scores")
@Data
public class JudgeScores implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer judgeScoresId;

    /**
     * 评委id
     */
    private Integer judgerId;

    /**
     * 比赛对战id
     */
    private Integer matchpkId;

    /**
     * 被评分的参赛选手id
     */
    private Integer playerId;

    /**
     * 评委所打分数
     */
    private Double judgeScoresScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}