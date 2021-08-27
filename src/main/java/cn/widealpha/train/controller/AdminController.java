package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("addTrain")
    ResultEntity addTrain(
            @RequestParam String trainNo,
            @RequestParam String stationTrainCode,
            @RequestParam String startStationTelecode,
            @RequestParam Time startStartTime,
            @RequestParam String endStationTelecode,
            @RequestParam Time endArriveTime,
            @RequestParam String trainTypeCode,
            @RequestParam String trainClassCode,
            @RequestParam String seatTypes,
            @RequestParam java.sql.Date startDate,
            @RequestParam java.sql.Date stopDate){

        return ResultEntity.data();
    }
}
