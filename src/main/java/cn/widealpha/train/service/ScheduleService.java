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
        for (int i = 0; i < 10; i++) {
            try {
                LocalDate d1 = date.plusDays(1);
                System.out.println("更新" + d1 + "数据成功,共更新" + stationWayMapper.copyStationBase(d1.toString()) + "条数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
