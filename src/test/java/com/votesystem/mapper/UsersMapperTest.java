package com.votesystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.votesystem.domain.Users;
import com.votesystem.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
class UsersMapperTest {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    UsersService usersService;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    void test() {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        List<Users> usersList = usersMapper.selectList(queryWrapper);
//        System.out.println("usersList = " + usersList);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println("encode = " + encode);
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_password", encode);
    }

    @Test
    void test1() {
        Users users = new Users();
        users.setUserAccount("root");
        users.setUserPassword(new BCryptPasswordEncoder().encode("123456"));
        users.setUserScale("admin");
        users.setUserSex(1);
        users.setUserTelephone("15223638640");
        users.setUserRealName("张三");
        users.setUserStatus(1);
        int insert = usersMapper.insert(users);
        System.out.println("insert = " + insert);
    }

    @Test
    void test02() {
        Users users = new Users();
        users.setUserAccount("admin");
        users.setUserId(192);
        users.setUserSex(0);
        users.setUserTelephone(null);
        LambdaUpdateWrapper<Users> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Users::getUserId, users.getUserId());
        System.out.println("users = " + users);
        int i = usersMapper.updateById(users);
        System.out.println("i = " + i);
    }

    @Test
    void test03() {
        Users users1 = new Users();
        users1.setUserId(191);
        users1.setUserAccount("admin");
        List<Users> users = usersService.findUsers(users1);
        System.out.println("users = " + users);
    }

    @Test
    void test04() {
        Users users = new Users();
        users.setUserAccount("admin");
        users.setUserStatus(1);
        users.setUserSex(1);
        int updateUsersInfo = usersMapper.updateUsersInfo(users);
        System.out.println("updateUsersInfo = " + updateUsersInfo);
    }

    @Test
    void Test05() {
        System.out.println(encoder.encode("123456"));
    }

    @Test
    void selectUsersInfoByUsersAccount() {
        Users users = new Users();
//        users.setUserAccount("admin");
        users.setUserScale("admin");
        Users admin = usersMapper.selectUsersInfoByUsersAccount("admin");
        System.out.println("admin = " + admin);
    }
    @Test
    void selectUsersInfoByUsersAccountOrUsersScale(){
        Users users = new Users();
//        users.setUserAccount("admin");
        users.setUserScale("singer");
        List<Users> usersList = usersMapper.selectUsersInfoByUsersAccountOrUsersScale(users);
        System.out.println("usersList = " + usersList);
    }
}
