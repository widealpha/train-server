package cn.widealpha.train.service;

import cn.widealpha.train.dao.CoachMapper;
import cn.widealpha.train.domain.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachService {
    @Autowired
    CoachMapper coachMapper;

    public Coach getCoach(int coachId) {
        return coachMapper.selectCoachByCoachId(coachId);
    }
}
