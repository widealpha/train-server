package cn.widealpha.train.service;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.*;
import cn.widealpha.train.domain.*;
import cn.widealpha.train.util.UserUtil;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    TicketMapper ticketMapper;
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

    public Ticket ticketInfo(int ticketId) {
        return ticketMapper.selectTicketByTicketId(ticketId);
    }

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
            if (stationWays.isEmpty()) {
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

    @Transactional
    public ResultEntity buyTicket(String startTelecode, String endTelecode, String stationTrainCode, String seatTypeCode, int passengerId, boolean student, String date, Character preferSeat) {
        if (UserUtil.getCurrentUserId() == null) {
            return ResultEntity.error(StatusCode.USER_NOT_LOGIN);
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
        if (userInfo.getSelfPassengerId() == null) {
            return ResultEntity.error(StatusCode.NO_REAL_NAME);
        }
        List<Passenger> passengers = passengerMapper.selectPassengersByUserId(UserUtil.getCurrentUserId());
        Passenger passenger = null;
        for (Passenger p : passengers) {
            if (p.getPassengerId().equals(passengerId)) {
                if (!p.getVerified()) {
                    return ResultEntity.error(StatusCode.NO_VERIFY);
                }
                if (student && !p.getStudentVerified()) {
                    return ResultEntity.error(StatusCode.NO_VERIFY);
                }
                passenger = p;
            }
        }
        if (passenger == null) {
            return ResultEntity.error(StatusCode.NO_PASSENGER);
        }
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train == null) {
            return ResultEntity.error(StatusCode.NO_TRAIN);
        }
        List<Coach> coachList = coachMapper.selectCoachByStationTrainCodeAndSeatType(stationTrainCode, seatTypeCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return ResultEntity.error(StatusCode.NO_TRAIN);
        }
        //查看是否车票时间冲突
        List<Ticket> tickets = ticketMapper.selectTicketByPassengerId(passengerId);
        Timestamp startTime = timeToTimeStamp(date, stationTrains.get(0).getStartTime());
        Timestamp arriveTime = timeToTimeStamp(date, stationTrains.get(stationTrains.size() - 1).getArriveTime());
        for (Ticket ticket : tickets) {
            if (ticket.getStartTime().getTime() >= startTime.getTime()
                    && ticket.getStartTime().getTime() <= arriveTime.getTime()) {
                return ResultEntity.error(StatusCode.TIME_CONFLICT);
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
        if (preferSeat != null) {
            int model = preferSeat - 'A';
            BigInteger bigInteger = BigInteger.ZERO;
            for (int i = model; i < 64; i += 4) {
                bigInteger = bigInteger.setBit(i);
            }
            for (Coach coach : coachList) {
                //找到第一个有空闲的车厢,并且座位符合要求的
                BigInteger seatNeed = coach.getSeat().and(bigInteger);
                if (seatNeed.bitCount() > 0) {
                    int bitPlace = seatNeed.getLowestSetBit();
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
                    if (stationTrainCode.startsWith("K")) {
                        price = price * 0.3;
                        price += (100 - price % 100);
                    }
                    if (student && (seatTypeCode.equals("A1") || seatTypeCode.equals("WZ"))) {
                        price = price * 0.5;
                    } else if (student) {
                        price = price * 0.75;
                    }
                    Ticket ticket = new Ticket();
                    ticket.setStudent(student);
                    ticket.setCoachId(coach.getCoachId());
                    ticket.setStartStationTelecode(stationTrains.get(0).getStationTelecode());
                    ticket.setEndStationTelecode(lastStationTrain.getStationTelecode());
                    ticket.setStationTrainCode(stationTrainCode);
                    ticket.setSeat(BigInteger.ZERO.setBit(bitPlace));
                    ticket.setStartTime(timeToTimeStamp(date, stationTrains.get(0).getStartTime()));
                    ticket.setEndTime(timeToTimeStamp(date, stationTrains.get(stationTrains.size() - 1).getArriveTime()));
                    ticket.setPassengerId(passengerId);
                    ticket.setPrice(price);
                    ticketMapper.insertTicket(ticket);
                    return ResultEntity.data(ticket.getTicketId());
                }
            }
        }
        //上面没成功再不切换座位来一次
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
                Ticket ticket = new Ticket();
                ticket.setStudent(student);
                ticket.setCoachId(coach.getCoachId());
                ticket.setStartStationTelecode(stationTrains.get(0).getStationTelecode());
                ticket.setEndStationTelecode(lastStationTrain.getStationTelecode());
                ticket.setStationTrainCode(stationTrainCode);
                ticket.setSeat(BigInteger.ZERO.setBit(bitPlace));
                ticket.setStartTime(timeToTimeStamp(date, stationTrains.get(0).getStartTime()));
                ticket.setEndTime(timeToTimeStamp(date, stationTrains.get(stationTrains.size() - 1).getArriveTime()));
                ticket.setPassengerId(passengerId);
                ticket.setPrice(price);
                ticketMapper.insertTicket(ticket);
                return ResultEntity.data(ticket.getTicketId());
            }
        }
        return ResultEntity.error(StatusCode.NO_FREE_TICKET);
    }

    @Transactional
    public StatusCode cancelTicket(int ticketId) {
        Ticket ticket = ticketMapper.selectTicketByTicketId(ticketId);
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
                    ticketMapper.deleteTicker(ticketId);
                }
                lastStationTrain = stationTrain;
            }
            return StatusCode.SUCCESS;
        }
    }

    @Transactional
    public ResultEntity changeTicket(int ticketId, String stationTrainCode) {
        if (UserUtil.getCurrentUserId() == null) {
            return ResultEntity.error(StatusCode.NO_PERMISSION);
        }
        if (!ticketMapper.ticketBelongToUserId(ticketId, UserUtil.getCurrentUserId())) {
            return ResultEntity.error(StatusCode.NO_PERMISSION);
        }
        Ticket ticket = ticketMapper.selectTicketByTicketId(ticketId);
        Coach coach = coachMapper.selectCoachByCoachId(ticket.getCoachId());
        cancelTicket(ticketId);
        ResultEntity resultEntity = buyTicket(ticket.getStartStationTelecode(), ticket.getEndStationTelecode(), stationTrainCode, coach.getSeatTypeCode(), ticket.getPassengerId(), ticket.getStudent(), ticket.getStartTime().toString().substring(0, 10), null);
        if (resultEntity.getCode() == 0) {
            Ticket newTicket = ticketMapper.selectTicketByTicketId((int) resultEntity.getData());
            double price = newTicket.getPrice() - ticket.getPrice();
            OrderForm orderForm = new OrderForm();
            orderForm.setPrice(price);
            orderForm.setUserId(UserUtil.getCurrentUserId());
            orderFormMapper.insertOrderForm(orderForm);
            ticketMapper.insertTicketOrderLink(newTicket.getTicketId(), orderForm.getOrderId());
            orderForm = orderFormMapper.selectOrderFormByOrderId(orderForm.getOrderId());
            if (orderForm.getPrice() <= 0){
                orderForm.setPayed(1);
                orderFormMapper.updateOrderForm(orderForm);
            }
            return ResultEntity.data(orderForm);
        }
        return ResultEntity.error(StatusCode.NO_TRAIN);
    }

    public List<Ticket> userTickets() {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        return ticketMapper.selectTicketByUserId(UserUtil.getCurrentUserId());
    }

    public List<Ticket> selfTickets() {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
        if (userInfo.getSelfPassengerId() == null) {
            return new ArrayList<>();
        }
        return ticketMapper.selectTicketByPassengerIdEnsurePayed(userInfo.getSelfPassengerId());
    }

    public List<Ticket> passengerTicket(int passengerId) {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        for (Passenger passenger : passengerMapper.selectPassengersByUserId(passengerId)) {
            if (passenger.getPassengerId() == passengerId) {
                return ticketMapper.selectTicketByPassengerId(passengerId);
            }
        }
        return new ArrayList<>();
    }

    public List<Ticket> ticketInfoByOrder(int orderId) {
        return ticketMapper.selectTicketByOrderFormId(orderId);
    }

    private Timestamp timeToTimeStamp(String date, Time time) {
        return Timestamp.valueOf(date + ' ' + time.toString());
    }
}
