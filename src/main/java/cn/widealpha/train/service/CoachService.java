package cn.widealpha.train.service;

import cn.widealpha.train.dao.CoachMapper;
import cn.widealpha.train.dao.StationTrainMapper;
import cn.widealpha.train.dao.StationWayMapper;
import cn.widealpha.train.domain.Coach;
import cn.widealpha.train.domain.StationWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CoachService {
    @Autowired
    CoachMapper coachMapper;
    @Autowired
    StationWayMapper stationWayMapper;
    @Autowired
    StationTrainMapper stationTrainMapper;

    public Coach getCoach(int coachId) {
        Coach coach =  coachMapper.selectCoachByCoachId(coachId);
        coach.setSeatCount(coach.getSeat().bitCount());
        return coach;
    }

    public List<Coach> getCoachByStationTrainCode(String stationTrainCode){
        List<Coach> coachList = coachMapper.selectCoachByStationTrainCode(stationTrainCode);
        for(Coach coach : coachList){
            coach.setSeatCount(coach.getSeat().bitCount());
        }
        return coachList;
    }

    @Transactional
    public boolean updateCoach(int coachId, int seatCount, String seatTypeCode){
        BigInteger seat = BigInteger.ZERO;
        for (int i = 0; i < seatCount; i++) {
            seat = seat.setBit(i);
        }
        coachMapper.updateCoachByCoachId(coachId, seatTypeCode, seat);
        stationWayMapper.updateStationWayBaseByCoachId(seat, coachId);
        return true;
    }


    @Transactional
    public boolean addCoach(int coachNo, int seatCount, String seatTypeCode, String stationTrainCode){
        BigInteger seat = BigInteger.ZERO;
        for (int i = 0; i < seatCount; i++) {
            seat = seat.setBit(i);
        }
        Coach coach = new Coach();
        coach.setCoachNo(coachNo);
        coach.setSeat(seat);
        coach.setSeatTypeCode(seatTypeCode);
        coach.setStationTrainCode(stationTrainCode);
        if (coachMapper.insertCoach(coach) > 0){
            List<String> stations = stationTrainMapper.selectTelecodeByStationTrain(coach.getStationTrainCode());
            String lastStation = null;
            for (String station : stations) {
                if (lastStation == null) {
                    lastStation = station;
                    continue;
                }
                StationWay stationWay = new StationWay();
                stationWay.setSeat(coach.getSeat());
                stationWay.setCoachId(coach.getCoachId());
                stationWay.setStartStationTelecode(lastStation);
                stationWay.setEndStationTelecode(station);
                stationWayMapper.insertStationWay(stationWay);
                lastStation = station;
            }
            return true;
        }
        return true;
    }

    @Transactional
    public boolean deleteCoach(int coachId) {
        if (coachMapper.deleteCoachByCoachId(coachId)) {
            return stationWayMapper.deleteStationWayByCoachId(coachId);
        }
        return false;
    }

}
