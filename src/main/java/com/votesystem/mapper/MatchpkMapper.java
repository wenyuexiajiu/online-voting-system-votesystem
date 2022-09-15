package com.votesystem.mapper;

import com.votesystem.domain.Matchpk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.votesystem.domain.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Ricardo
 * @description 针对表【matchpk】的数据库操作Mapper
 * @createDate 2022-07-06 10:17:16
 * @Entity com.votesystem.domain.Matchpk
 */
@Mapper
public interface MatchpkMapper extends BaseMapper<Matchpk> {
    /**
     * 根据角色查询用户信息
     *
     * @param users 角色
     * @return
     */
    List<Users> selectSingerByUserScale(Users users);

    /**
     * 查找已配对成功的选手的比赛信息
     * 根据比赛场次进行查找
     *
     * @param matchpk
     * @return
     */
    List<Matchpk> selectMatched(Matchpk matchpk);

    /**
     * 添加选手匹配信息
     *
     * @param matchpk
     * @return
     */
    @Insert("insert into matchpk(match_id,player_a, player_music_a,player_b,player_music_b, matchPK_status) values(#{matchId},#{playerA},#{playerMusicA},#{playerB},#{playerMusicB},#{matchpkStatus})")
    int insertMatchInfo(Matchpk matchpk);

    /**
     * 更新比赛状态
     *
     * @param matchpk
     * @return
     */
    @Update("update matchpk set matchPK_status=#{matchpkStatus} where match_id=#{matchId} and player_a=#{playerA} and player_b=#{playerB}")
    int updateMatchStatus(Matchpk matchpk);

    /**
     * 判断是否有比赛开启
     * 或根据比赛场次id查询已开启的比赛
     *
     * @param matchpk
     * @return
     */
    @Select("select player_a, player_music_a,player_b,player_music_b, matchPK_status,match_id from matchpk where matchPK_status=1 and match_id=#{matchId}")
    Matchpk selectMatchInfoByMatchStatusAndMatchId(Matchpk matchpk);

    /**
     * 查找对战场次
     *
     * @return
     */
    @Select("select distinct match_id from matchpk")
    List<Integer> selectMatchIds();
}




