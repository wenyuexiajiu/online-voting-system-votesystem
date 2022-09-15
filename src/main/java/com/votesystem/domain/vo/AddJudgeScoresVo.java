package com.votesystem.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Ricardo
 * @TableName judge_scores
 */
@Data
public class AddJudgeScoresVo implements Serializable {
    /**
     * 自增主键
     */
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
    /**
     * 评委的姓名
     */
    private String judgeName;
}