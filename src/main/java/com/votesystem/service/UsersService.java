package com.votesystem.service;

import com.votesystem.common.Result;
import com.votesystem.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【users】的数据库操作Service
 * @createDate 2022-07-06 14:05:37
 */
public interface UsersService extends IService<Users> {
    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    Result addUsers(Users user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    Result updateUsers(Users user);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    Result removeUsers(String id);

    /**
     * 查找用户信息
     *
     * @param user
     * @return
     */
    List<Users> findUsers(Users user);

    /**
     * 发送邮箱验证码
     *
     * @param toEmail 发送给谁的邮箱验证码
     * @return
     */
    String sendEmailCode(String toEmail);

    /**
     * 判断是否已经注册使用
     *
     * @param email
     * @return
     */
    Result isRegistered(String email);

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    Result updatePassword(Users user);

    /**
     * 实现用户头像上传
     *
     * @param multipartFile 头像文件
     * @param username      用户账号
     * @return
     */
    Result<Users> upLoadHeaderImg(MultipartFile multipartFile, String username);
}
