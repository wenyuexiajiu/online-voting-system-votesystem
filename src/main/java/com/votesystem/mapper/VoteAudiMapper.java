package com.votesystem.mapper;

import com.votesystem.domain.VoteAudi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ricardo
* @description 针对表【vote_audi】的数据库操作Mapper
* @createDate 2022-07-09 09:22:19
* @Entity com.votesystem.domain.VoteAudi
*/
@Mapper
public interface VoteAudiMapper extends BaseMapper<VoteAudi> {

}




