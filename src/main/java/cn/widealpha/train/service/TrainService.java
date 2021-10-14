package cn.widealpha.train.service;

import cn.widealpha.train.pojo.bo.TrainStationTime;
import cn.widealpha.train.pojo.dto.Pager;
import cn.widealpha.train.dao.*;
import cn.widealpha.train.pojo.bo.ChangeTrain;
import cn.widealpha.train.pojo.bo.TrainPrice;
import cn.widealpha.train.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TrainService {
    @Autowired
    TrainMapper trainMapper;
    @Autowired
    StationTrainMapper stationTrainMapper;
    @Autowired
    StationPriceMapper stationPriceMapper;
    @Autowired
    StationMapper stationMapper;
    @Autowired
    TrainClassMapper trainClassMapper;
    @Autowired
    SeatTypeMapper seatTypeMapper;
    @Autowired
    SystemSettingMapper systemSettingMapper;
    @Autowired
    CoachMapper coachMapper;
    @Autowired
    StationWayMapper stationWayMapper;
    @Autowired
    TicketService ticketService;

    public List<String> allStationTrainCode() {
        return trainMapper.allTrainCode();
    }

    public Pager<Train> getTrains(int page, int size) {
        Pager<Train> pager = new Pager<>();
        Integer count = trainMapper.count();
        if ((page - 1) * size >= count) {
            pager.setSize(size);
            pager.setPage(count / size);
            pager.setTotal(count);
            pager.setRows(new ArrayList<>());
            return pager;
        }
        pager.setSize(size);
        pager.setPage(page);
        pager.setTotal(count);
        List<Train> trains = trainMapper.selectTrains(page, size);
        for (Train train : trains) {
            train.setTrainStations(stationTrainMapper.selectStationTrainByTrainCode(train.getTrainCode()));
        }
        pager.setRows(trains);
        return pager;
    }

    public Train getTrainByName(String stationTrainCode) {
        Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
        if (train != null) {
            train.setTrainStations(stationTrainMapper.selectStationTrainByTrainCode(train.getTrainCode()));
        }
        return train;
    }

    /**
     * 返回两站之间列车信息
     *
     * @param startStationTelecode 出发站
     * @param endStationTelecode   终点站
     * @param simple               是否返回简略信息
     * @return 列车信息
     */
    public List<Train> getTrainByStation(String startStationTelecode, String endStationTelecode, String date, boolean simple) {
        List<Train> trains = new ArrayList<>();
        //扩展到相似站台
        List<String> startSameStations = stationMapper.selectSameStationTelecode(startStationTelecode);
        startSameStations.add(startStationTelecode);
        List<String> endSameStations = stationMapper.selectSameStationTelecode(endStationTelecode);
        endSameStations.add(endStationTelecode);
        for (String start : startSameStations) {
            for (String end : endSameStations) {
                // 查找所有的经过起点终点的列车号
                List<String> trainCodes = stationTrainMapper.selectTrainCodeByStartEnd(start, end);
                if (!trainCodes.isEmpty()) {
                    List<Train> trainList = trainMapper.selectTrainsByTrainCodes(trainCodes);
                    List<StationTrain> stationTrainList = stationTrainMapper.selectStationTrainByTrainCodes(trainCodes);
                    for (Train train : trainList) {
                        train.setTrainStations(new ArrayList<>());
                        for (StationTrain stationTrain : stationTrainList) {
                            if (stationTrain.getTrainCode().equals(train.getTrainCode())) {
                                train.getTrainStations().add(stationTrain);
                            }
                        }
                        train.setNowStartStationTelecode(start);
                        train.setNowEndStationTelecode(end);
                        // 简略信息不返回中途经过的站台的具体信息
                        if (!simple) {
                            train.setTrainStations(null);
                        }
                        trains.add(train);
                    }
                }
            }
        }
        return trains;
    }

    public List<StationTrain> getTrainStations(String stationTrainCode) {
        return stationTrainMapper.selectStationTrainByTrainCode(stationTrainCode);
    }

    /**
     * 获取换乘信息
     *
     * @param startStationTelecode 起始车站
     * @param endStationTelecode   终点车站
     * @param date                 日期
     * @return 返回换乘信息
     */
    public List<ChangeTrain> getTrainsBetweenWithChange(String startStationTelecode, String endStationTelecode, String date) {
        //取出数据库设置的最大换乘数量
        SystemSetting systemSetting = systemSettingMapper.selectSystemSetting();
        List<ChangeTrain> changeTrains = new ArrayList<>();
        List<String> firstTrains = stationTrainMapper.selectTrainCodeByStationTelecode(startStationTelecode);
        Map<String, TrainStationTime> firstTrainStartTime = stationTrainMapper.selectStartTimeByTelecode(startStationTelecode);
        Map<String, TrainStationTime> lastTrainArriveTime = stationTrainMapper.selectArriveTimeByTelecode(endStationTelecode);
        List<String> lastTrains = stationTrainMapper.selectTrainCodeByStationTelecode(endStationTelecode);
        List<StationTrain> firstStationTrains = stationTrainMapper.selectStationTrainByTrainCodes(firstTrains);
        List<StationTrain> lastStationTrains = stationTrainMapper.selectStationTrainByTrainCodes(lastTrains);

        Set<Map.Entry<String, String>> containedTrainPair = new HashSet<>();
        for (StationTrain first : firstStationTrains) {
            for (StationTrain last : lastStationTrains) {
                if (first.getStationTelecode().equals(last.getStationTelecode())
                        && !first.getTrainCode().equals(last.getTrainCode())) {
                    //排除查询到的在首发站/终点站换乘的情况
                    if (first.getArriveTime() == null || last.getStartTime() == null
                            || first.getStationTelecode().equals(endStationTelecode)
                            || last.getStationTelecode().equals(startStationTelecode)) {
                        continue;
                    }

                    // 换乘中转时间控制在20min-4h以内
                    LocalTime firstArriveTime = first.getArriveTime().toLocalTime();
                    LocalTime lastStartTime = last.getStartTime().toLocalTime();
                    int interval = (int) firstArriveTime.until(lastStartTime, ChronoUnit.MINUTES);
                    // 这里添加20min有可能跳到第二天,所以先判断是不是在后面
                    if (interval < 20 || interval > 240) {
                        continue;
                    }

                    LocalTime startTime = firstTrainStartTime.get(first.getTrainCode()).getTime().toLocalTime();
                    LocalTime arriveTime = lastTrainArriveTime.get(last.getTrainCode()).getTime().toLocalTime();
                    //排除辆车相遇的车站在出发车站以前,或在终点车站以后的情况
                    if (startTime.isAfter(firstArriveTime) || arriveTime.isBefore(lastStartTime)) {
                        continue;
                    }

                    ChangeTrain changeTrain = new ChangeTrain();
                    changeTrain.setFirstTrainCode(first.getTrainCode());
                    changeTrain.setFirstTrainArriveTime(first.getArriveTime());
                    changeTrain.setLastTrainCode(last.getTrainCode());
                    changeTrain.setLastTrainStartTime(last.getStartTime());

                    changeTrain.setChangeStation(first.getStationTelecode());
                    changeTrain.setInterval(interval);

                    changeTrain.setLength((int) startTime.until(arriveTime, ChronoUnit.MINUTES));
                    changeTrain.setLength(changeTrain.getLength()
                            + 24 * 60 * (lastTrainArriveTime.get(last.getTrainCode()).getDay() - firstTrainStartTime.get(first.getTrainCode()).getDay()));

                    Map.Entry<String, String> pair = new AbstractMap.SimpleEntry<>(first.getTrainCode(), last.getTrainCode());
                    if (containedTrainPair.contains(pair)) {
                        continue;
                    }
                    containedTrainPair.add(pair);
                    changeTrains.add(changeTrain);

                    // 添加过之后放进已添加查重
                    containedTrainPair.add(pair);
                }
            }
        }

        changeTrains.sort(Comparator.comparing(ChangeTrain::getLength));
        return changeTrains.subList(0, Math.min(changeTrains.size(), systemSetting.getMaxTransferCalculate()));
    }

    public List<TrainPrice> trainPrice(String startTelecode, String endTelecode, String stationTrainCode) {
        List<TrainPrice> trainPrices = new ArrayList<>();
        Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
        if (train == null) {
            return new ArrayList<>();
        }
        TrainClass trainClass = trainClassMapper.selectTrainClassByClassCode(train.getTrainClassCode());
        List<SeatType> seatTypes = seatTypeMapper.selectSeatTypeByTrainCode(stationTrainCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return new ArrayList<>();
        }
        //根据车座位类型初始化价格表
        for (SeatType seatType : seatTypes) {
            TrainPrice trainPrice = new TrainPrice();
            trainPrice.setTrainCode(stationTrainCode);
            trainPrice.setTrainClassCode(trainClass.getTrainClassCode());
            trainPrice.setTrainClassName(trainClass.getTrainClassName());
            trainPrice.setSeatTypeCode(seatType.getSeatTypeCode());
            trainPrice.setSeatTypeName(seatType.getSeatTypeName());
            trainPrice.setStartStationTelecode(startTelecode);
            trainPrice.setEndStationTelecode(endTelecode);
            trainPrice.setPrice(0.0);
            trainPrices.add(trainPrice);
        }
        StationTrain lastStationTrain = stationTrains.get(0);
        for (int i = 1; i < stationTrains.size(); i++) {
            StationTrain stationTrain = stationTrains.get(i);
            List<StationPrice> stationPrices = stationPriceMapper.selectStationPrice(lastStationTrain.getStationTelecode(), stationTrain.getStationTelecode(), train.getTrainClassCode());
            for (StationPrice stationPrice : stationPrices) {
                for (TrainPrice trainPrice : trainPrices) {
                    if (trainPrice.getSeatTypeCode().equals(stationPrice.getSeatTypeCode())) {
                        trainPrice.setPrice(trainPrice.getPrice() + stationPrice.getPrice());
                        break;
                    }
                }
            }
            lastStationTrain = stationTrain;
        }
        for (TrainPrice trainPrice : trainPrices) {
            if (stationTrainCode.startsWith("K")) {
                trainPrice.setPrice(trainPrice.getPrice() * 0.3);
                trainPrice.setPrice(trainPrice.getPrice() + (100 - trainPrice.getPrice() % 100));
            }
        }
        return trainPrices;
    }

    public boolean stopTrain(String stationTrainCode) {
        Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
        train.setStopDate(Date.valueOf("2000-01-01"));
        return trainMapper.updateTrainByTrainCode(train);
    }

    public boolean startTrain(String stationTrainCode) {
        Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
        train.setStopDate(Date.valueOf("2100-01-01"));
        return trainMapper.updateTrainByTrainCode(train);
    }

    @Transactional
    public boolean updateArriveTime(String stationTrainCode, String stationTelecode, int stationNo, String arriveTime) {
        StationTrain stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, stationTelecode, stationNo);
        if (arriveTime != null && !arriveTime.isEmpty()) {
            stationTrain.setUpdateArriveTime(Time.valueOf(arriveTime));
        } else {
            stationTrain.setUpdateArriveTime(null);
        }
        return stationTrainMapper.updateStationTrain(stationTrain);
    }

    @Transactional
    public boolean updateStartTime(String stationTrainCode, String stationTelecode, int stationNo, String startTime) {
        StationTrain stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, stationTelecode, stationNo);
        if (startTime != null && !startTime.isEmpty()) {
            stationTrain.setUpdateArriveTime(Time.valueOf(startTime));
        } else {
            stationTrain.setUpdateArriveTime(null);
        }
        return stationTrainMapper.updateStationTrain(stationTrain);
    }

    @Transactional
    public boolean updateTrainStation(String stationTrainCode, String stationTelecode, int stationNo, String updateStationTelecode, String arriveTime, String startTime, Integer startDayDiff, Integer arriveDayDiff) {
        StationTrain stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, stationTelecode, stationNo);
        List<Integer> stationNos = stationTrainMapper.selectStationNos(stationTrainCode);
        if (stationNo == stationNos.get(0)) {
            Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
            train.setStartStationTelecode(updateStationTelecode);
            trainMapper.updateTrainByTrainCode(train);
        } else if (stationNo == stationNos.get(stationNos.size() - 1)) {
            Train train = trainMapper.selectTrainByTrainCode(stationTrainCode);
            train.setEndStationTelecode(updateStationTelecode);
            trainMapper.updateTrainByTrainCode(train);
        }
        stationTrainMapper.updateStationTrainTelecode(stationTrain.getTrainCode(), stationTrain.getStationTelecode(), updateStationTelecode);
        stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, updateStationTelecode, stationNo);
        stationTrain.setStartTime(Time.valueOf(startTime + ":00"));
        stationTrain.setArriveTime(Time.valueOf(arriveTime + ":00"));
        if (startDayDiff != null) {
            stationTrain.setStartDayDiff(startDayDiff);
        }
        if (arriveDayDiff != null) {
            stationTrain.setArriveDayDiff(arriveDayDiff);
        }
        stationTrainMapper.updateStationTrain(stationTrain);
        return true;
    }

    @Transactional
    public boolean deleteTrainStation(String stationTrainCode, String stationTelecode) {
        List<Integer> stationNos = stationTrainMapper.selectStationNos(stationTrainCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByTrainCode(stationTrainCode);
        if (stationNos.size() <= 2) {
            return false;
        }
        for (int i = 1; i < stationTrains.size() - 1; i++) {
            if (stationTrains.get(i).getTrainCode().equals(stationTrainCode)) {
                stationWayMapper.deleteStationWayByKeyAndStationTrainCode(stationTrains.get(i - 1).getTrainCode(), stationTelecode, stationTrainCode);
                stationWayMapper.deleteStationWayByKeyAndStationTrainCode(stationTelecode, stationTrains.get(i + 1).getTrainCode(), stationTrainCode);
                stationTrainMapper.deleteStationTrainByKey(stationTrainCode, stationTelecode);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean addTrainStation(String stationTrainCode, String stationTelecode, int stationNo, String arriveTime, String startTime, Integer startDayDiff, Integer arriveDayDiff) {
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByTrainCode(stationTrainCode);
        for (int i = 0; i < stationTrains.size() - 1; i++) {
            if (stationTrains.get(i).getStationNo() < stationNo && stationTrains.get(i + 1).getStationNo() > stationNo) {
                StationTrain stationTrain = new StationTrain();
                stationTrain.setTrainCode(stationTrainCode);
                stationTrain.setStationTelecode(stationTelecode);
                stationTrain.setStartTime(Time.valueOf(startTime + ":00"));
                stationTrain.setArriveTime(Time.valueOf(arriveTime + ":00"));
                stationTrain.setStartDayDiff(startDayDiff);
                stationTrain.setArriveDayDiff(arriveDayDiff);
                stationTrain.setStationNo(stationNo);
                stationTrainMapper.insertStationTrain(stationTrain);
                stationWayMapper.deleteStationWayByKeyAndStationTrainCode(stationTrains.get(i).getTrainCode(), stationTrains.get(i + 1).getTrainCode(), stationTrainCode);
                List<Coach> coachList = coachMapper.selectCoachByTrainCode(stationTrainCode);
                for (Coach coach : coachList) {
                    StationWay stationWay = new StationWay();
                    stationWay.setCoachId(coach.getCoachId());
                    stationWay.setSeat(coach.getSeat());
                    stationWay.setStartStationTelecode(stationTrains.get(i).getTrainCode());
                    stationWay.setEndStationTelecode(stationTelecode);
                    stationWayMapper.insertStationWay(stationWay);
                    stationWay.setStartStationTelecode(stationTelecode);
                    stationWay.setEndStationTelecode(stationTrains.get(i + 1).getTrainCode());
                    stationWayMapper.insertStationWay(stationWay);
                }
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean addTrain(String stationTrainCode, String startStationTelecode, String endStationTelecode, String startTime, String endTime, int arriveDayDiff) {
        Train train = new Train();
        train.setTrainNo("0000000" + stationTrainCode);
        train.setTrainCode(stationTrainCode);
        train.setStartStationTelecode(startStationTelecode);
        train.setEndStationTelecode(endStationTelecode);
        train.setStartStartTime(Time.valueOf(startTime + ":00"));
        train.setEndArriveTime(Time.valueOf(endTime + ":00"));
        train.setSeatTypes("29360128");
        train.setStartDate(Date.valueOf("2000-01-01"));
        train.setStopDate(Date.valueOf("2100-01-01"));
        if (stationTrainCode.startsWith("K")) {
            train.setTrainClassCode("0");
        } else if (stationTrainCode.startsWith("G")) {
            train.setTrainClassCode("8");
        }
        trainMapper.insertTrain(train);
        StationTrain stationTrain = new StationTrain();
        stationTrain.setTrainCode(stationTrainCode);
        stationTrain.setStationTelecode(startStationTelecode);
        stationTrain.setStartTime(Time.valueOf(startTime + ":00"));
        stationTrain.setArriveTime(Time.valueOf(startTime + ":00"));
        stationTrain.setStartDayDiff(0);
        stationTrain.setArriveDayDiff(0);
        stationTrain.setStationNo(100);
        stationTrainMapper.insertStationTrain(stationTrain);

        stationTrain.setStationTelecode(endStationTelecode);
        stationTrain.setStartTime(Time.valueOf(endTime + ":00"));
        stationTrain.setArriveTime(Time.valueOf(endTime + ":00"));
        stationTrain.setStartDayDiff(arriveDayDiff);
        stationTrain.setArriveDayDiff(arriveDayDiff);
        stationTrain.setStationNo(2);
        stationTrainMapper.insertStationTrain(stationTrain);

        StationWay stationWay = new StationWay();
        stationWay.setCoachId(1);
        stationWay.setStartStationTelecode(startStationTelecode);
        stationWay.setEndStationTelecode(endStationTelecode);
        stationWayMapper.insertStationWay(stationWay);

        return true;
    }
}
