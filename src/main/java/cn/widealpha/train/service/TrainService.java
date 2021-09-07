package cn.widealpha.train.service;

import cn.widealpha.train.bean.Pager;
import cn.widealpha.train.dao.*;
import cn.widealpha.train.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
        return trainMapper.allStationTrainCode();
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
            train.setTrainStations(stationTrainMapper.selectStationTrainByStationTrainCode(train.getStationTrainCode()));
        }
        pager.setRows(trains);
        return pager;
    }

    public Train getTrainByName(String stationTrainCode) {
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train != null) {
            train.setTrainStations(stationTrainMapper.selectStationTrainByStationTrainCode(train.getStationTrainCode()));
        }
        return train;
    }

    public List<Train> getTrainByStation(String startStationTelecode, String endStationTelecode, String date) {
        List<Train> trains = new ArrayList<>();
        List<String> startSameStations = stationMapper.selectSameStationTelecode(startStationTelecode);
        startSameStations.add(startStationTelecode);
        List<String> endSameStations = stationMapper.selectSameStationTelecode(endStationTelecode);
        endSameStations.add(endStationTelecode);
        for (String start : startSameStations) {
            for (String end : endSameStations) {
                List<String> trainCodes = stationTrainMapper.selectStationTrainCodeByStartEnd(start, end);
                if (!trainCodes.isEmpty()) {
                    List<Train> trainList = trainMapper.selectTrainsByStationTrainCodes(trainCodes);
                    for (Train train : trainList) {
                        train.setTrainStations(stationTrainMapper.selectStationTrainByStationTrainCode(train.getStationTrainCode()));
                        train.setNowStartStationTelecode(start);
                        train.setNowEndStationTelecode(end);
                    }
                    trains.addAll(trainList);
                }
            }
        }
        return trains;
    }

    public List<StationTrain> getTrainStations(String stationTrainCode) {
        return stationTrainMapper.selectStationTrainByStationTrainCode(stationTrainCode);
    }

    public List<ChangeTrain> getTrainsBetweenWithChange(String startStationTelecode, String endStationTelecode, String date) {
        //取出数据库设置的最大换乘数量
        SystemSetting systemSetting = systemSettingMapper.selectSystemSetting();
        List<ChangeTrain> changeTrains = new ArrayList<>();
        List<String> trains = stationTrainMapper.selectStationTrainCodeByStationTelecode(startStationTelecode);
        Set<String> passStation = new HashSet<>();
        //遍历所有列车取得可能换乘的车站
        for (String train : trains) {
            passStation.addAll(stationTrainMapper.selectTelecodeByStationTrainAfter(train, startStationTelecode));
        }
        for (String station : passStation) {
            List<StationTrain> firstTrains = stationTrainMapper.selectStationTrainByStartEnd(startStationTelecode, station);
            List<StationTrain> lastTrains = stationTrainMapper.selectStationTrainByStartEnd(station, endStationTelecode);
            for (StationTrain first : firstTrains) {
                for (StationTrain last : lastTrains) {
                    //优化效率
                    if (changeTrains.size() > systemSetting.getMaxTransferCalculate()) {
                        return changeTrains;
                    }
                    //排除始发站情况
                    if (first.getStationNo() == 1) {
                        continue;
                    }
                    if (first.getArriveTime() == null) {
                        first.setArriveTime(first.getStartTime());
                    }
                    if (last.getStartTime() == null) {
                        last.setStartTime(last.getArriveTime());
                    }
                    if (last.getStartDayDiff() > first.getArriveDayDiff()
                            || (last.getStartTime().after(first.getArriveTime())
                            && last.getStartDayDiff() >= first.getArriveDayDiff())) {
                        LocalTime lastTime = last.getStartTime().toLocalTime();
                        LocalTime firstTime = first.getArriveTime().toLocalTime();
                        //如果后一辆换乘列车需要等待的时间大于1小时,或者换乘时间小于5分钟,不加入此换乘计划
                        if (lastTime.minusHours(2).isAfter(firstTime) || lastTime.minusMinutes(5).isBefore(firstTime)) {
                            continue;
                        }
                        //当天的车辆如果已经发车,不进行添加
                        if (LocalDate.now().toString().equals(date)) {
                            if (first.getStartTime().toLocalTime().isBefore(LocalTime.now())) {
                                continue;
                            }
                        }
                        ChangeTrain changeTrain = new ChangeTrain();
                        changeTrain.setChangeStation(station);

                        changeTrain.setFirstStationTrainCode(first.getStationTrainCode());
                        Train firstTrain = trainMapper.selectTrainByStationTrainCode(first.getStationTrainCode());
                        firstTrain.setNowStartStationTelecode(startStationTelecode);
                        firstTrain.setNowEndStationTelecode(station);
                        firstTrain.setTrainStations(stationTrainMapper.selectStationTrainByStationTrainCode(first.getStationTrainCode()));
                        changeTrain.setFirstTrain(firstTrain);

                        changeTrain.setLastStationTrainCode(last.getStationTrainCode());
                        Train lastTrain = trainMapper.selectTrainByStationTrainCode(last.getStationTrainCode());
                        lastTrain.setNowStartStationTelecode(station);
                        lastTrain.setNowEndStationTelecode(endStationTelecode);
                        lastTrain.setTrainStations(stationTrainMapper.selectStationTrainByStationTrainCode(last.getStationTrainCode()));
                        changeTrain.setLastTrain(lastTrain);

                        changeTrain.setFirstTrainArriveTime(first.getArriveTime());
                        changeTrain.setLastTrainStartTime(last.getStartTime());
                        if (last.getStartDayDiff() > first.getArriveDayDiff()) {
                            changeTrain.setInterval(24 * 60 + lastTime.getMinute() + lastTime.getHour() * 60 - firstTime.getMinute() - firstTime.getHour() * 60);
                        } else {
                            changeTrain.setInterval(lastTime.getMinute() + lastTime.getHour() * 60 - firstTime.getMinute() - firstTime.getHour() * 60);
                        }
                        boolean shouldAdd = true;
                        for (ChangeTrain c : changeTrains) {
                            if (c.getFirstStationTrainCode().equals(changeTrain.getFirstStationTrainCode())
                                    && c.getLastStationTrainCode().equals(changeTrain.getLastStationTrainCode())) {
                                shouldAdd = false;
                                break;
                            }
                        }
                        if (shouldAdd) {
                            changeTrains.add(changeTrain);
                        }
                    }
                }
            }
        }
        return changeTrains;
    }

    public List<TrainPrice> trainPrice(String startTelecode, String endTelecode, String stationTrainCode) {
        List<TrainPrice> trainPrices = new ArrayList<>();
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        if (train == null) {
            return new ArrayList<>();
        }
        TrainClass trainClass = trainClassMapper.selectTrainClassByTrainCode(train.getTrainClassCode());
        List<SeatType> seatTypes = seatTypeMapper.selectSeatTypeByStationTrainCode(stationTrainCode);
        List<StationTrain> stationTrains = stationTrainMapper.selectStationTrainByStartEndCode(startTelecode, endTelecode, stationTrainCode);
        if (stationTrains.size() <= 1) {
            return new ArrayList<>();
        }
        //根据车座位类型初始化价格表
        for (SeatType seatType : seatTypes) {
            TrainPrice trainPrice = new TrainPrice();
            trainPrice.setStationTrainCode(stationTrainCode);
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
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        train.setStopDate(Date.valueOf("2000-01-01"));
        return trainMapper.updateTrainByStationTrainCode(train);
    }

    public boolean startTrain(String stationTrainCode) {
        Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
        train.setStopDate(Date.valueOf("2100-01-01"));
        return trainMapper.updateTrainByStationTrainCode(train);
    }

    public boolean updateArriveTime(String stationTrainCode, String stationTelecode, int stationNo, String arriveTime) {
        StationTrain stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, stationTelecode, stationNo);
        if (arriveTime != null && !arriveTime.isEmpty()) {
            stationTrain.setUpdateArriveTime(Time.valueOf(arriveTime));
        } else {
            stationTrain.setUpdateArriveTime(null);
        }
        return stationTrainMapper.updateStationTrain(stationTrain);
    }

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
    public boolean updateTrainStation(String stationTrainCode, String stationTelecode, int stationNo, String updateStationTelecode) {
        StationTrain stationTrain = stationTrainMapper.selectStationTrainByKey(stationTrainCode, stationTelecode, stationNo);
        List<Integer> stationNos = stationTrainMapper.selectStationNos(stationTrainCode);
        if (stationNo == stationNos.get(0)) {
            Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
            train.setStartStationTelecode(updateStationTelecode);
            trainMapper.updateTrainByStationTrainCode(train);
        } else if (stationNo == stationNos.get(stationNos.size() - 1)) {
            Train train = trainMapper.selectTrainByStationTrainCode(stationTrainCode);
            train.setEndStationTelecode(updateStationTelecode);
            trainMapper.updateTrainByStationTrainCode(train);
        }
        stationTrainMapper.updateStationTrainTelecode(stationTrain.getStationTrainCode(), stationTrain.getStationTelecode(), stationTrain.getStationNo(), updateStationTelecode);
        return true;
    }
}
