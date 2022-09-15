package com.votesystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 账户密码
     */
    private String userPassword;

    /**
     * 用户姓名
     */
    private String userRealName;

    /**
     * 性别（1：男，0：女）
     */
    private Integer userSex;

    /**
     * 联系电话
     */
    private String userTelephone;

    /**
     * 权限控制（audience:观众，admin：管理员，singer：歌手）
     */
    private String userScale;

    /**
     * 状态位（1：有效，0无效）
     */
    private Integer userStatus;

    /**
     * 比赛对战外键
     */
    private Integer matchpkId;

    /**
     * 1：已晋级；0：未晋级
     */
    private Integer playerPromotion;

    /**
     * 选手作品
     */
    private String playerMusic;

    /**
     * 用户头像
     */
    private String userImg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}