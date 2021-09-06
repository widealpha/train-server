package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.service.CoachService;
import cn.widealpha.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYSTEM')")
@RequestMapping("admin")
public class AdminController {
    @Autowired
    TrainService trainService;
    @Autowired
    CoachService coachService;

    @RequestMapping("addTrain")
    ResultEntity addTrain() {
        return ResultEntity.data();
    }

    @RequestMapping("stopTrain")
    ResultEntity stopTrain(@RequestParam String stationTrainCode) {
        return ResultEntity.data(trainService.stopTrain(stationTrainCode));
    }

    @RequestMapping("startTrain")
    ResultEntity startTrain(@RequestParam String stationTrainCode) {
        return ResultEntity.data(trainService.startTrain(stationTrainCode));
    }

    @RequestMapping("updateStartTime")
    ResultEntity updateStartTime(@RequestParam String stationTrainCode, @RequestParam String stationTelecode,
                                 @RequestParam int stationNo, @RequestParam String startTime) {
        return ResultEntity.data(trainService.updateStartTime(stationTrainCode, stationTelecode, stationNo, startTime));
    }

    @RequestMapping("updateArriveTime")
    ResultEntity updateArriveTime(@RequestParam String stationTrainCode, @RequestParam String stationTelecode,
                                  @RequestParam int stationNo, @RequestParam String arriveTime) {
        return ResultEntity.data(trainService.updateArriveTime(stationTrainCode, stationTelecode, stationNo, arriveTime));
    }

    @RequestMapping("updateCoach")
    ResultEntity updateCoach(@RequestParam int coachId, @RequestParam int seatCount, @RequestParam String seatTypeCode) {
        return ResultEntity.data(coachService.updateCoach(coachId, seatCount, seatTypeCode));
    }

    @RequestMapping("addCoach")
    ResultEntity addCoach(@RequestParam int coachNo, @RequestParam int seatCount, @RequestParam String seatTypeCode, @RequestParam String stationTrainCode) {
        return ResultEntity.data(coachService.addCoach(coachNo, seatCount, seatTypeCode, stationTrainCode));
    }

    @RequestMapping("deleteCoach")
    ResultEntity deleteCoach(@RequestParam int coachId) {
        return ResultEntity.data(coachService.deleteCoach(coachId));
    }
}
