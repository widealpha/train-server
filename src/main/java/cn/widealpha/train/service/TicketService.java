package cn.widealpha.train.service;

import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.*;
import cn.widealpha.train.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TrainMapper trainMapper;
    @Autowired
    StationTrainMapper stationTrainMapper;
    @Autowired
    StationMapper stationMapper;
    @Autowired
    TrainClassMapper trainClassMapper;
    @Autowired
    SeatTypeMapper seatTypeMapper;
    @Autowired
    StationWayMapper stationWayMapper;
    @Autowired
    CoachMapper coachMapper;

    public List<TrainTicketRemain> trainTicketRemain(String startTelecode, String endTelecode, String stationTrainCode) {
        List<TrainTicketRemain> trainTicketRemains = new ArrayList<>();
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train == null) {
            return new ArrayList<>();
        }
        TrainClass trainClass = trainClassMapper.selectTrainClassByTrainCode(train.getTrainClassCode());
        List<Coach> coachList = coachMapper.selectCoachByStationTrainCode(stationTrainCode);
        List<SeatType> seatTypes = seatTypeMapper.selectSeatTypeByStationTrainCode(stationTrainCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return new ArrayList<>();
        }
        //根据车座位类型初始化剩余座位表
        for (SeatType seatType : seatTypes) {
            TrainTicketRemain trainTicketRemain = new TrainTicketRemain();
            trainTicketRemain.setStationTrainCode(stationTrainCode);
            trainTicketRemain.setTrainClassCode(trainClass.getTrainClassCode());
            trainTicketRemain.setTrainClassName(trainClass.getTrainClassName());
            trainTicketRemain.setSeatTypeCode(seatType.getSeatTypeCode());
            trainTicketRemain.setSeatTypeName(seatType.getSeatTypeName());
            trainTicketRemain.setStartStationTelecode(startTelecode);
            trainTicketRemain.setEndStationTelecode(endTelecode);
            trainTicketRemain.setRemaining(0);
            trainTicketRemain.setDate(new Date());
            trainTicketRemains.add(trainTicketRemain);
        }

        StationTrain lastStationTrain = stationTrains.get(0);
        //对两个相邻站台遍历
        for (int i = 1; i < stationTrains.size(); i++) {
            StationTrain stationTrain = stationTrains.get(i);
            List<StationWay> stationWays = stationWayMapper.selectStationWayByStartEnd(lastStationTrain.getStationTelecode(), stationTrain.getStationTelecode());
            //对经过同一段路的同一辆车的多个车厢遍历
            for (StationWay stationWay : stationWays) {
                //遍历车厢的详细信息,通过位运算与修改剩余座位
                for (Coach coach: coachList){
                    if (coach.getCoachId().equals(stationWay.getCoachId())){
                        coach.setSeat(coach.getSeat().and(stationWay.getSeat()));
                        break;
                    }
                }
            }
            lastStationTrain = stationTrain;
        }
        //计算每种座位的数量
        for (TrainTicketRemain remain: trainTicketRemains){
            for (Coach coach: coachList){
                if (coach.getSeatTypeCode().equals(remain.getSeatTypeCode())){
                    remain.setRemaining(remain.getRemaining() + coach.getSeat().bitCount());
                }
            }
        }
        return trainTicketRemains;
    }

    @Transient
    public StatusCode buyTicket(String startTelecode, String endTelecode, String stationTrainCode, String seatTypeCode){
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train == null) {
            return StatusCode.NO_TRAIN;
        }
        List<Coach> coachList = coachMapper.selectCoachByStationTrainCodeAndSeatType(stationTrainCode, seatTypeCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return StatusCode.NO_TRAIN;
        }
        StationTrain lastStationTrain = stationTrains.get(0);
        //对两个相邻站台遍历,计算出中间车厢空余的车票位置,结果储存在coach的seat中
        for (int i = 1; i < stationTrains.size(); i++) {
            StationTrain stationTrain = stationTrains.get(i);
            List<StationWay> stationWays = stationWayMapper.selectStationWayByStartEnd(lastStationTrain.getStationTelecode(), stationTrain.getStationTelecode());
            //对经过同一段路的同一辆车的多个车厢遍历
            for (StationWay stationWay : stationWays) {
                //遍历车厢的详细信息,通过位运算与修改剩余座位,seat中每一位都是可用座位
                for (Coach coach: coachList){
                    if (coach.getCoachId().equals(stationWay.getCoachId())){
                        coach.setSeat(coach.getSeat().and(stationWay.getSeat()));
                        break;
                    }
                }
            }
            lastStationTrain = stationTrain;
        }
        for (Coach coach: coachList){
            //找到第一个有空闲的车厢
            if (coach.getSeat().bitCount() > 0){
                int bitPlace = coach.getSeat().getLowestSetBit();
                BigInteger seat = coach.getSeat().clearBit(bitPlace);
                //遍历所有相邻站台修改座位
                lastStationTrain = stationTrains.get(0);
                for (int i = 1; i < stationTrains.size(); i++) {
                    StationTrain stationTrain = stationTrains.get(i);
                    StationWay stationWay = new StationWay();
                    stationWay.setStartStationTelecode(lastStationTrain.getStationTelecode());
                    stationWay.setEndStationTelecode(stationTrains.get(i).getStationTelecode());
                    stationWay.setCoachId(coach.getCoachId());
                    stationWay = stationWayMapper.selectStationWaysByKey(stationWay);
                    stationWay.setSeat(stationWay.getSeat().clearBit(bitPlace));
                    stationWayMapper.updateStationWaySeat(stationWay);
                    lastStationTrain = stationTrain;
                }
                return StatusCode.SUCCESS;
            }
        }
        return StatusCode.NO_TICKET;
    }
}
