package com.votesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.votesystem.domain.Users;
import com.votesystem.domain.vo.CustomUser;
import com.votesystem.exception.CustomersException;
import com.votesystem.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricardo
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
//        根据用户名查找用户
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserAccount, username);
        Users users = usersMapper.selectOne(wrapper);
//        若用户为空，则抛出异常
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //判断账号是否有效
        if (users.getUserStatus() == 0) {
            throw new UsernameNotFoundException("账户已失效");
        } else {
            List<GrantedAuthority> list = new ArrayList<>();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            list.add(new SimpleGrantedAuthority(users.getUserScale()));

            CustomUser user = new CustomUser(users.getUserAccount(), users.getUserPassword(), list);
            user.setRoleName(users.getUserScale());
            user.setUserId(users.getUserId());
            user.setUserRealName(users.getUserRealName());
            return user;
        }
    }
}
