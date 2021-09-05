package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("coach")
@RestController
public class CoachController {
    @Autowired
    CoachService coachService;

    @RequestMapping("coachInfo")
    ResultEntity coachInfo(int coachId) {
        return ResultEntity.data(coachService.getCoach(coachId));
    }
}
