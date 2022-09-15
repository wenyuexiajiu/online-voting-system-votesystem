package com.votesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.common.Result;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.CustomUser;
import com.votesystem.exception.CustomersException;
import com.votesystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author Ricardo
 * 用于处理公共部分的API接口
 */
@Controller
@RequestMapping("/common")
@SessionAttributes({"usersInfo", "user"})
public class CommonController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录成功后的地址
     *
     * @param authentication
     * @return
     */
    @RequestMapping("/index")
    @ResponseBody
    public Result index(Authentication authentication, Model model) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        Users users = usersService.getById(user.getUserId());
        model.addAttribute("user", users);
        model.addAttribute("usersInfo", user);
        return Result.success(200, "登录成功！", user);
    }

    /**
     * 主页面路径映射
     *
     * @return
     */
    @RequestMapping("/main")
    public String main(Model model) {
        List<Users> usersList = usersService.findUsers(null);
//        model.addAttribute("userList", usersList);
        model.addAttribute("userNumbers", usersList.size());
        return "main";
    }

    /**
     * 登录失败后的情况
     */
    @RequestMapping("/errors")
    public void error() {
        throw new CustomersException(500, "登录失败");
    }


    /**
     * 添加404地址映射
     *
     * @return
     */
    @RequestMapping("/404")
    public String noFoundPage() {
        return "404";
    }


    /**
     * 注册地址映射
     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/personInfo")
    public String personInfo() {
        return "personInfo";
    }

    /**
     * 实现注册功能
     *
     * @return
     */
    @GetMapping("/doRegister")
    public String doRegister() {
        return "register";
    }

    /**
     * 获取验证码逻辑
     *
     * @return
     */
    @GetMapping("/getCode")
    @ResponseBody
    public String getEmailCode() {
        return "5201314";
    }

    /**
     * 邮箱注册验证码发送
     *
     * @param email
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/send/email", method = RequestMethod.POST)
    @ResponseBody
    public Result sendEmail(String email) {
        String emailCode = usersService.sendEmailCode(email);
        return Result.success(200, "发送验证码成功！", emailCode);
    }

    /**
     * 判断是否账号注册冲突
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/isRegister", method = RequestMethod.POST)
    @ResponseBody
    public Result isRegister(String email) {
        return usersService.isRegistered(email);
    }

    /**
     * 注册成功
     *
     * @param user
     * @return
     */
    @PostMapping("/doRegister")
    @ResponseBody
    public Result doRegister(Users user) {
        System.out.println("user = " + user);
        return usersService.addUsers(user);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @GetMapping("/editPassword")
    public String editPassword() {
        return "edit_password";
    }

    /**
     * 更改密码
     *
     * @param authentication
     * @param user
     * @return
     */
    @PutMapping("/doEditPassword")
    @ResponseBody
    public Result doEditPassword(Authentication authentication, Users user) {
        user.setUserAccount(authentication.getName());
        return usersService.updatePassword(user);
    }

    @PostMapping("/uploadHeadImg")
    @ResponseBody
    public Result uploadHeadImg(MultipartFile file, String username, Model model) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUserAccount, username);

        Result<Users> usersResult = usersService.upLoadHeaderImg(file, username);
        Users one = usersService.getOne(queryWrapper);
        model.addAttribute("user", one);
        usersResult.setData(one);
        return usersResult;
    }
}
