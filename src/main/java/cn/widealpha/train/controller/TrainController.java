package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.service.TicketService;
import cn.widealpha.train.service.TrainService;
import cn.widealpha.train.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("train")
public class TrainController {
    @Autowired
    TrainService trainService;
    @Autowired
    TicketService ticketService;

    @RequestMapping("allTrains")
    ResultEntity allTrains(@RequestParam Integer page, @RequestParam Integer size) {
        if (page == null || size == null || page == 0 || size == 0) {
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(trainService.getTrains(page, size));
    }

    @RequestMapping("trainInfo")
    ResultEntity trainInfo(@RequestParam String stationTrainCode) {
        if (stationTrainCode == null) {
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(trainService.getTrainByName(stationTrainCode));
    }

    @RequestMapping("trainsBetween")
    ResultEntity trainsBetween(@RequestParam String startStationTelecode, @RequestParam String endStationTelecode) {
        return ResultEntity.data(trainService.getTrainByStation(startStationTelecode, endStationTelecode));
    }

    @RequestMapping("trainsBetweenWithChange")
    ResultEntity trainsBetweenWithChange(@RequestParam String startStationTelecode, @RequestParam String endStationTelecode){
        return ResultEntity.data(trainService.getTrainsBetweenWithChange(startStationTelecode, endStationTelecode));
    }

    @RequestMapping("trainPrice")
    ResultEntity trainPrice(@RequestParam String stationTrainCode, @RequestParam String startStationTelecode, @RequestParam String endStationTelecode) {
        if (StringUtil.anyEmpty(startStationTelecode, endStationTelecode, stationTrainCode)){
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(trainService.trainPrice(startStationTelecode, endStationTelecode, stationTrainCode));
    }

    @RequestMapping("trainTicketRemaining")
    ResultEntity trainTicketRemaining(@RequestParam String stationTrainCode, @RequestParam String startStationTelecode, @RequestParam String endStationTelecode, @RequestParam String date){
        if (StringUtil.anyEmpty(startStationTelecode, endStationTelecode, stationTrainCode, date)){
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(ticketService.trainTicketRemain(startStationTelecode, endStationTelecode, stationTrainCode, date));
    }

    @RequestMapping("trainStations")
    ResultEntity trainStations(@RequestParam String stationTrainCode){
        return ResultEntity.data(trainService.getTrainStations(stationTrainCode));
    }
}
