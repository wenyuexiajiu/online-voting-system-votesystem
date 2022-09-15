package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.votesystem.common.Result;
import com.votesystem.domain.Users;
import com.votesystem.exception.CustomersException;
import com.votesystem.service.UsersService;
import com.votesystem.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Ricardo
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2022-07-06 14:05:37
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Value("${filePath.location}")
    private String rootPath;

    private String codeEmail;

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Result addUsers(Users user) {
//        加密密码
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        user.setUserStatus(1);
        if (user.getUserScale() == null) {
            user.setUserScale("audience");
        }
        int insert = usersMapper.insert(user);
        if (insert != 1) {
            throw new CustomersException(500, "注册失败!");
        } else {
            return Result.success(200, "注册成功！", user);
        }
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUsers(Users user) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUserAccount, user.getUserAccount());
//        先判断之前密码
//        Users users = usersMapper.selectUsers(user).get(0);
        Users users = usersMapper.selectOne(queryWrapper);
//        若前后密码不一致，则需要进行加密
        if (!users.getUserPassword().equals(user.getUserPassword())) {
            user.setUserPassword(encoder.encode(user.getUserPassword()));
        }

        int update = usersMapper.updateUsersInfo(user);
        System.out.println("update = " + update);
        if (update != 1) {
            throw new CustomersException(500, "更新失败");
        } else {
            return Result.success(200, "更新成功！", null);
        }
    }

    /**
     * 删除用户信息
     *
     * @param id user_status,user_id
     * @return
     */
    @Override
    public Result removeUsers(String id) {
        Users user = new Users();
        user.setUserAccount(id);
        user.setUserStatus(2);
        int update = usersMapper.updateUsersInfo(user);
        if (update != 1) {
            throw new CustomersException(500, "删除失败");
        } else {
            return Result.success(200, "删除成功！", null);
        }
    }

    /**
     * 查找用户信息
     *
     * @param user
     * @return
     */
    @Override
    public List<Users> findUsers(Users user) {
        List<Users> usersList = usersMapper.selectUsers(user);
        return usersList;
    }

    /**
     * 发送邮箱验证码
     *
     * @param toEmail 发送给谁的邮箱验证码
     * @return
     */
    @Override
    public String sendEmailCode(String toEmail) {
        //生成随机验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);

        SimpleMailMessage message = new SimpleMailMessage();
        //发件人邮箱
        message.setFrom(fromEmail);
        //抄送
        message.setCc("在线投票网站@qq.com");
        //目标邮箱
        message.setTo(toEmail);
        //标题
        message.setSubject("这是一个邮件主题——系统邮件");
        //内容
        message.setText("邮箱注册验证\n您好： " + toEmail + "\n验证码：您正在使用该账号进行注册功能," + checkCode +
                "\n验证码提供给他人可能导致账号被盗，请勿转发或泄露。" + "\n 如非本人操作，请忽略本消息，谢谢!");
        //发送邮箱
        mailSender.send(message);

        return checkCode;
    }

    /**
     * 判断是否已经注册使用
     *
     * @param email
     * @return
     */
    @Override
    public Result isRegistered(String email) {
        Result result;
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUserAccount, email);
        List<Users> usersList = usersMapper.selectList(queryWrapper);

        if (usersList.size() > 0) {
            result = Result.error(500, "该邮箱已被注册");
        } else {
            result = Result.success(200, "可以使用", null);
        }
        return result;
    }

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    @Override
    public Result updatePassword(Users user) {
        //        先判断之前密码
        Users users = usersMapper.selectUsers(user).get(0);
//        若前后密码不一致，则需要进行加密
        if (!users.getUserPassword().equals(user.getUserPassword())) {
            users.setUserPassword(encoder.encode(user.getUserPassword()));
        }
        int update = usersMapper.updateUsersInfo(users);
        if (update != 1) {
            throw new CustomersException(500, "更新失败");
        } else {
            return Result.success(200, "更新成功！", null);
        }
    }

    /**
     * 实现用户头像上传
     *
     * @param multipartFile 头像文件
     * @param username      用户账号
     * @return
     */
    @Override
    public Result<Users> upLoadHeaderImg(MultipartFile multipartFile, String username) {
        if (multipartFile == null) {
            return Result.success(600, "内容没有改变，不需要进行保存~", null);
        }
        if (multipartFile.getSize() > 2097152) {
            return Result.success(600, "上传图片大小不能超过2MB", null);
        }
        System.out.println("multipartFile = " + multipartFile.getSize());
        //更新条件构造器
        LambdaUpdateWrapper<Users> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Users::getUserAccount, username);
        //查询构造器
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUserAccount, username);

        //获取文件文件名
        String realPaths = multipartFile.getOriginalFilename();

        // uuid 生成文件名，随机文件名
        String fileName = UUID.randomUUID() + realPaths;

        //获取文件的后缀名
        String suffixName = realPaths.substring(realPaths.lastIndexOf("."));

//        获取绝对路径
        String absolutePath = new File(rootPath).getAbsolutePath();

        //读取存入的文件路径
        String dirPath = absolutePath;

        //上传文件
        File targetFile = new File(absolutePath);

//        判断文件夹是否存在，若不存在，则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //根据新上传的头像来创建File对象。
        File file = new File(dirPath, fileName);

        //判断数据库中是否已经有图片了，若有图片了，需要先找到该图片的路径，然后将其替换为新的图片
        Users users = usersMapper.selectOne(updateWrapper);

        //先将新的头像信息存入项目文件夹中
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            throw new CustomersException(400, "上传头像失败，请稍后再试~");
        }
        //判断是否已经传入成功
        if (!file.exists()) {
            throw new CustomersException(400, "上传头像失败，请稍后再试~");
        }
        String beforeFileName = null;
        //拿到数据库中的头像信息
        if (users.getUserImg() != null) {
            //存在之前的头像
            //根据之前存在头像的文件信息创建File对象
            beforeFileName = users.getUserImg();
        }

        //    并更新到数据库中
        Users newUserImg = new Users();
        newUserImg.setUserImg(fileName);
        int update = usersMapper.update(newUserImg, updateWrapper);
        if (update != 1) {
            throw new CustomersException(400, "上传头像失败，请稍后再试~");
        }

        if (beforeFileName != null) {
            File beforeFile = new File(dirPath, beforeFileName);
            //    判断文件夹中是否还存在原来的头像文件
            if (beforeFile.exists()) {
                //说明存在，需要在删除前把新的头像信息更新到数据库中
                boolean delete = beforeFile.delete();
                //if (!delete) {
                //    throw new CustomersException(300, "头像覆盖失败，请稍后再试~");
                //}
            }
        }

        //需要在执行一次查询语句，查询出user的信息
        Users selectOne = usersMapper.selectOne(queryWrapper);
        return Result.success(200, "头像上传成功，页面即将自动刷新~", selectOne);
    }

}




