package com.votesystem.mapper;

import com.votesystem.domain.Matchpk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchpkMapperTest {

    @Autowired
    MatchpkMapper matchpkMapper;

    @Test
    void selectSingerByUserScale() {
        Matchpk matchpk = new Matchpk();
        List<Matchpk> matchpks = matchpkMapper.selectMatched(matchpk);
        System.out.println("matchpks = " + matchpks);
    }
}