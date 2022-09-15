package com.votesystem;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.domain.Users;
import com.votesystem.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoteSystemApplicationTests {

    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private UsersMapper usersMapper;

    @Test
    void contextLoads() {
        System.out.println("dataSource = " + dataSource);
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserAccount, "admin");
        Users users = usersMapper.selectOne(wrapper);
        System.out.println("users = " + users);
    }
}
