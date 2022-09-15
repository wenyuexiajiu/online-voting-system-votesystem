package com.votesystem.controller;


import com.votesystem.common.Result;
import com.votesystem.domain.Users;
import com.votesystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ricardo
 * 观众操作
 */
@Controller
@RequestMapping("/audience")
public class AudienceController {
    @Autowired
    private UsersService usersService;

    /**
     * 新增观众
     *
     * @param users
     * @return
     */
    @PostMapping("/addAudience")
    @ResponseBody
    public Result addAudience(Users users) {
        return usersService.addUsers(users);
    }

    /**
     * 查找指定观众
     *
     * @return
     */
    @GetMapping("/queryAudience")
    public String findAudience(Users user, Model model) {
        List<Users> usersList = usersService.findUsers(user);
        model.addAttribute("userList", usersList);
        return "";
    }

    /**
     * 根据id删除观众账号
     *
     * @return
     */
    @DeleteMapping("/deleteAudience/{id}")
    @ResponseBody
    public Result removeAudience(@PathVariable String id) {
        return usersService.removeUsers(id);
    }


}
