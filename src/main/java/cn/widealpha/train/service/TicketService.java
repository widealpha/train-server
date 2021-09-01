package cn.widealpha.train.service;

import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.*;
import cn.widealpha.train.domain.*;
import cn.widealpha.train.util.UserUtil;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
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
    @Autowired
    TickerMapper tickerMapper;
    @Autowired
    OrderFormMapper orderFormMapper;
    @Autowired
    PassengerMapper passengerMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    TrainService trainService;
    @Autowired
    OrderFormService orderFormService;

    public List<TrainTicketRemain> trainTicketRemain(String startTelecode, String endTelecode, String stationTrainCode, String date) {
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
            trainTicketRemain.setDate(date);
            trainTicketRemains.add(trainTicketRemain);
        }

        StationTrain lastStationTrain = stationTrains.get(0);
        //对两个相邻站台遍历
        for (int i = 1; i < stationTrains.size(); i++) {
            StationTrain stationTrain = stationTrains.get(i);
            List<StationWay> stationWays = stationWayMapper.selectStationWayByStartEnd(lastStationTrain.getStationTelecode(), stationTrain.getStationTelecode(), date);
            //未查询到车之间信息
            if (stationWays.isEmpty()){
                return new ArrayList<>();
            }
            //对经过同一段路的同一辆车的多个车厢遍历
            for (StationWay stationWay : stationWays) {
                //遍历车厢的详细信息,通过位运算与修改剩余座位
                for (Coach coach : coachList) {
                    if (coach.getCoachId().equals(stationWay.getCoachId())) {
                        coach.setSeat(coach.getSeat().and(stationWay.getSeat()));
                        break;
                    }
                }
            }
            lastStationTrain = stationTrain;
        }
        //计算每种座位的数量
        for (TrainTicketRemain remain : trainTicketRemains) {
            for (Coach coach : coachList) {
                if (coach.getSeatTypeCode().equals(remain.getSeatTypeCode())) {
                    remain.setRemaining(remain.getRemaining() + coach.getSeat().bitCount());
                }
            }
        }
        return trainTicketRemains;
    }

    @Transient
    public StatusCode buyTicket(String startTelecode, String endTelecode, String stationTrainCode, String seatTypeCode, int passengerId, boolean student, String date) {
        if (UserUtil.getCurrentUserId() == null) {
            return StatusCode.USER_NOT_LOGIN;
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
        if (userInfo.getSelfPassengerId() == null) {
            return StatusCode.NO_REAL_NAME;
        }
        List<Passenger> passengers = passengerMapper.selectPassengersByUserId(UserUtil.getCurrentUserId());
        Passenger passenger = null;
        for (Passenger p : passengers) {
            if (p.getPassengerId().equals(passengerId)) {
                if (!p.getVerified()) {
                    return StatusCode.NO_VERIFY;
                }
                if (student && !p.getStudentVerified()) {
                    return StatusCode.NO_VERIFY;
                }
                passenger = p;
            }
        }
        if (passenger == null) {
            return StatusCode.NO_PASSENGER;
        }
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train == null) {
            return StatusCode.NO_TRAIN;
        }
        List<Coach> coachList = coachMapper.selectCoachByStationTrainCodeAndSeatType(stationTrainCode, seatTypeCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return StatusCode.NO_TRAIN;
        }
        //查看是否车票时间冲突
        List<Ticket> tickets = tickerMapper.selectTicketByPassengerId(passengerId);
        Timestamp startTime = timeToTimeStamp(date, stationTrains.get(0).getStartTime());
        Timestamp arriveTime = timeToTimeStamp(date, stationTrains.get(stationTrains.size() - 1).getArriveTime());
        for (Ticket ticket: tickets){
            if (ticket.getStartTime().getTime() >= startTime.getTime()
                    && ticket.getStartTime().getTime() <= arriveTime.getTime()){
                return StatusCode.TIME_CONFLICT;
            }
        }
        StationTrain lastStationTrain = stationTrains.get(0);
        //对两个相邻站台遍历,计算出中间车厢空余的车票位置,结果储存在coach的seat中
        for (int i = 1; i < stationTrains.size(); i++) {
            StationTrain stationTrain = stationTrains.get(i);
            List<StationWay> stationWays = stationWayMapper.selectStationWayByStartEnd(lastStationTrain.getStationTelecode(), stationTrain.getStationTelecode(), date);
            //对经过同一段路的同一辆车的多个车厢遍历
            for (StationWay stationWay : stationWays) {
                //遍历车厢的详细信息,通过位运算与修改剩余座位,seat中每一位都是可用座位
                for (Coach coach : coachList) {
                    if (coach.getCoachId().equals(stationWay.getCoachId())) {
                        coach.setSeat(coach.getSeat().and(stationWay.getSeat()));
                        break;
                    }
                }
            }
            lastStationTrain = stationTrain;
        }

        for (Coach coach : coachList) {
            //找到第一个有空闲的车厢
            if (coach.getSeat().bitCount() > 0) {
                int bitPlace = coach.getSeat().getLowestSetBit();
                //遍历所有相邻站台修改座位
                lastStationTrain = stationTrains.get(0);
                for (int i = 1; i < stationTrains.size(); i++) {
                    StationTrain stationTrain = stationTrains.get(i);
                    StationWay stationWay = new StationWay();
                    stationWay.setStartStationTelecode(lastStationTrain.getStationTelecode());
                    stationWay.setEndStationTelecode(stationTrains.get(i).getStationTelecode());
                    stationWay.setCoachId(coach.getCoachId());
                    stationWay.setDate(date);
                    stationWay = stationWayMapper.selectStationWaysByKey(stationWay);
                    stationWay.setSeat(stationWay.getSeat().clearBit(bitPlace));
                    stationWayMapper.updateStationWaySeat(stationWay);
                    lastStationTrain = stationTrain;
                }
                double price = 0;
                //计算车票价格
                List<TrainPrice> trainPriceList = trainService.trainPrice(
                        stationTrains.get(0).getStationTelecode(), lastStationTrain.getStationTelecode(), stationTrainCode);
                for (TrainPrice trainPrice : trainPriceList) {
                    if (trainPrice.getSeatTypeCode().equals(seatTypeCode)) {
                        price = trainPrice.getPrice();
                    }
                }
                if (student && (seatTypeCode.equals("A1") || seatTypeCode.equals("WZ"))) {
                    price = price * 0.5;
                } else if (student) {
                    price = price * 0.75;
                }
                OrderForm orderForm = new OrderForm();
                orderForm.setUserId(UserUtil.getCurrentUserId());
                orderForm.setPrice(price);
                orderFormMapper.insertOrderForm(orderForm);
                Ticket ticket = new Ticket();
                ticket.setStudent(student);
                ticket.setOrderId(orderForm.getOrderId());
                ticket.setCoachId(coach.getCoachId());
                ticket.setStartStationTelecode(stationTrains.get(0).getStationTelecode());
                ticket.setEndStationTelecode(lastStationTrain.getStationTelecode());
                ticket.setStationTrainCode(stationTrainCode);
                ticket.setSeat(BigInteger.ZERO.setBit(bitPlace));
                ticket.setStartTime(timeToTimeStamp(date, stationTrains.get(0).getStartTime()));
                ticket.setEndTime(timeToTimeStamp(date, stationTrains.get(stationTrains.size() - 1).getArriveTime()));
                ticket.setPassengerId(passengerId);
                ticket.setPrice(price);
                tickerMapper.insertTicket(ticket);
                return StatusCode.SUCCESS;
            }
        }
        return StatusCode.NO_FREE_TICKET;
    }

    @Transient
    public StatusCode cancelTicket(int ticketId) {
        Ticket ticket = tickerMapper.selectTicketByTicketId(ticketId);
        if (ticket == null) {
            return StatusCode.NO_DATA_EXIST;
        } else {
            OrderForm orderForm = orderFormMapper.selectOrderFormByOrderId(ticket.getOrderId());
            if (!orderForm.getUserId().equals(UserUtil.getCurrentUserId())) {
                return StatusCode.NO_PERMISSION;
            }
            List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(
                    ticket.getStartStationTelecode(), ticket.getEndStationTelecode(), ticket.getStationTrainCode());
            StationTrain lastStationTrain = stationTrains.get(0);
            //对两个相邻站台遍历,取出seat信息
            for (int i = 1; i < stationTrains.size(); i++) {
                StationTrain stationTrain = stationTrains.get(i);
                StationWay stationWay = new StationWay();
                stationWay.setStartStationTelecode(lastStationTrain.getStationTelecode());
                stationWay.setEndStationTelecode(stationTrains.get(i).getStationTelecode());
                stationWay.setCoachId(ticket.getCoachId());
                stationWay.setDate(ticket.getStartTime().toString().substring(0, 10));
                stationWay = stationWayMapper.selectStationWaysByKey(stationWay);
                //位与运算加上售出的票
                stationWay.setSeat(stationWay.getSeat().or(ticket.getSeat()));
                if (stationWayMapper.updateStationWaySeat(stationWay)) {
                    tickerMapper.deleteTicker(ticketId);
                    try {
                        if (orderFormService.cancelOrderForm(ticket.getOrderId()) == StatusCode.SUCCESS){
                            orderForm.setPrice(-ticket.getPrice());
                            orderFormMapper.insertOrderForm(orderForm);
                        }
                    } catch (AlipayApiException e){
                        System.out.println(ticketId + "退款失败");
                        return StatusCode.COMMON_FAIL;
                    }
                }
                lastStationTrain = stationTrain;
            }
            return StatusCode.SUCCESS;
        }
    }

    public StatusCode changeTicket(int ticketId, String stationTrainCode) {
        if (UserUtil.getCurrentUserId() == null) {
            return StatusCode.NO_PERMISSION;
        }
        if (!tickerMapper.ticketBelongToUserId(ticketId, UserUtil.getCurrentUserId())) {
            return StatusCode.NO_PERMISSION;
        }
        Ticket ticket = tickerMapper.selectTicketByTicketId(ticketId);
        Coach coach = coachMapper.selectCoachByCoachId(ticket.getCoachId());
        if (buyTicket(ticket.getStartStationTelecode(), ticket.getEndStationTelecode(), stationTrainCode, coach.getSeatTypeCode(), ticket.getPassengerId(), ticket.getStudent(), ticket.getStartTime().toString().substring(0, 10)) == StatusCode.SUCCESS) {
            cancelTicket(ticketId);
            return StatusCode.SUCCESS;
        }
        return StatusCode.NO_TRAIN;
    }

    public List<Ticket> userTickets() {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        return tickerMapper.selectTicketByUserId(UserUtil.getCurrentUserId());
    }

    public List<Ticket> passengerTicket(int passengerId) {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        for (Passenger passenger : passengerMapper.selectPassengersByUserId(passengerId)) {
            if (passenger.getPassengerId() == passengerId) {
                return tickerMapper.selectTicketByPassengerId(passengerId);
            }
        }
        return new ArrayList<>();
    }

    private Timestamp timeToTimeStamp(String date, Time time) {
        return Timestamp.valueOf(date + ' ' + time.toString());
    }
}
