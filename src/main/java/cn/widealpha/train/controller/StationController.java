package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("station")
public class StationController {
    @Autowired
    StationService stationService;

    @RequestMapping("allStations")
    public ResultEntity allStation() {
        return ResultEntity.data(stationService.allStation());
    }

    @RequestMapping("info")
    public ResultEntity stationInfo(@RequestParam String stationTelecode) {
        return ResultEntity.data(stationService.getStationByTelecode(stationTelecode));
    }


}
