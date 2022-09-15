package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName matchpk
 */
@TableName(value ="matchpk")
@Data
public class Matchpk implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 比赛id
     */
    private Integer matchId;

    /**
     * 参赛选手a的id外键
     */
    private Integer playerA;

    /**
     * 参赛选手a的歌曲
     */
    private String playerMusicA;

    /**
     * 参赛选手a的id外键
     */
    private Integer playerB;

    /**
     * 参赛选手a的歌曲
     */
    private String playerMusicB;

    /**
     * 比赛状态（1：开启，0：关闭，2：暂停中）
     */
    private Integer matchpkStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}