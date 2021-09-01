package cn.widealpha.train.service;

import cn.widealpha.train.dao.StationWayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduleService {
    @Autowired
    StationWayMapper stationWayMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void copyStationWayBase() {
        LocalDate date = LocalDate.now();
        System.out.println("更新数据成功,共更新" + stationWayMapper.copyStationBase(date.toString()) + "条数据");
    }
}
