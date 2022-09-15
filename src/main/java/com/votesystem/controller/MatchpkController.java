package com.votesystem.controller;

import com.votesystem.domain.Matchpk;
import com.votesystem.service.MatchpkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Ricardo
 * 比赛管理controller
 */
@Controller
@RequestMapping("/match")
public class MatchpkController {

    @Autowired
    private MatchpkService matchpkService;
}
