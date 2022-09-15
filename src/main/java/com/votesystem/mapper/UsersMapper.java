package com.votesystem.mapper;

import com.votesystem.domain.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【users】的数据库操作Mapper
 * @createDate 2022-07-06 14:05:37
 * @Entity com.votesystem.domain.Users
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 根据用户相关信息查找用户
     *
     * @param user
     * @return
     */
    List<Users> selectUsers(Users user);

    /**
     * 用户更新操作
     *
     * @param user
     * @return
     */
    int updateUsersInfo(Users user);

    /**
     * 根据用户的账号查找用户的基本信息
     *
     * @param userAccount
     * @return
     */
    Users selectUsersInfoByUsersAccount(String userAccount);

    /**
     * 根据用户的账号或角色查找用户的基本信息
     *
     * @param user
     * @return
     */
    List<Users> selectUsersInfoByUsersAccountOrUsersScale(Users user);

}




