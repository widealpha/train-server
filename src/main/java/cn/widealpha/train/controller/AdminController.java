package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.pojo.entity.SeatType;
import cn.widealpha.train.pojo.entity.TrainClass;
import cn.widealpha.train.service.*;
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
    @Autowired
    PriceService priceService;
    @Autowired
    OrderFormService orderFormService;
    @Autowired
    SeatTypeService seatTypeService;
    @Autowired
    TrainClassService trainClassService;
    @Autowired
    PassengerService passengerService;
    @Autowired
    UserInfoService userInfoService;


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

    @RequestMapping("updateTrainStation")
    ResultEntity updateTrainStation(@RequestParam String stationTrainCode, @RequestParam String stationTelecode,
                                    @RequestParam int stationNo, @RequestParam String updateStationTelecode, @RequestParam String arriveTime, @RequestParam String startTime, @RequestParam Integer startDayDiff, @RequestParam Integer arriveDayDiff) {
        return ResultEntity.data(trainService.updateTrainStation(stationTrainCode, stationTelecode, stationNo, updateStationTelecode, arriveTime, startTime, startDayDiff, arriveDayDiff));
    }

    @RequestMapping("addTrainStation")
    ResultEntity addTrainStation(@RequestParam String stationTrainCode, @RequestParam String stationTelecode,
                                 @RequestParam int stationNo, @RequestParam String arriveTime, @RequestParam String startTime, @RequestParam Integer startDayDiff, @RequestParam Integer arriveDayDiff) {
        return ResultEntity.data(trainService.addTrainStation(stationTrainCode, stationTelecode, stationNo, arriveTime, startTime, startDayDiff, arriveDayDiff));
    }


    @RequestMapping("deleteTrainStation")
    ResultEntity deleteTrainStation(@RequestParam String stationTrainCode, @RequestParam String stationTelecode, @RequestParam int stationNo) {
        return ResultEntity.data(trainService.deleteTrainStation(stationTrainCode, stationTelecode));
    }

    @RequestMapping("updateTrainClassPriceRatio")
    ResultEntity updateTrainClassPriceRatio(@RequestParam String trainClassCode, @RequestParam double ratio) {
        return ResultEntity.data(priceService.updateTrainClassPriceRatio(trainClassCode, ratio));
    }

    @RequestMapping("updateSeatTypePriceRatio")
    ResultEntity updateSeatPriceRatio(@RequestParam String seatTypeCode, @RequestParam double ratio) {
        return ResultEntity.data(priceService.updateSeatPriceRatio(seatTypeCode, ratio));
    }

    @RequestMapping("updateStationPriceRatio")
    ResultEntity updateStationPriceRatio(@RequestParam String startStationTelecode, String endStationTelecode, @RequestParam double ratio) {
        return ResultEntity.data(priceService.updateStationPriceRatio(startStationTelecode, endStationTelecode, ratio));
    }

    @RequestMapping("getSellByTime")
    ResultEntity getSellByTime() {
        return ResultEntity.data(orderFormService.getSellByTime());
    }

    @RequestMapping("getSellByTrainClass")
    ResultEntity getSellByTrainClass() {
        return ResultEntity.data(orderFormService.getSellByTrainClass());
    }

    @RequestMapping("renameTrainClassName")
    ResultEntity renameTrainClassName(@RequestParam String trainClassCode, @RequestParam String trainClassName) {
        TrainClass trainClass = new TrainClass();
        trainClass.setTrainClassCode(trainClassCode);
        trainClass.setTrainClassName(trainClassName);
        return ResultEntity.data(trainClassService.renameTrainClass(trainClass));
    }

    @RequestMapping("renameSeatTypeName")
    ResultEntity renameSeatTypeName(@RequestParam String seatTypeCode, @RequestParam String seatTypeName) {
        SeatType seatType = new SeatType();
        seatType.setSeatTypeCode(seatTypeCode);
        seatType.setSeatTypeName(seatTypeName);
        return ResultEntity.data(seatTypeService.renameSeatType(seatType));
    }

    @RequestMapping("allPassengers")
    public ResultEntity allPassengers() {
        return ResultEntity.data(passengerService.allPassengers());
    }

    @RequestMapping("addTrain")
    public ResultEntity addTrain(@RequestParam String stationTrainCode, @RequestParam String startStationTelecode, @RequestParam String endStationTelecode, @RequestParam String startTime, @RequestParam String endTime, @RequestParam int arriveDayDiff) {
        boolean b = trainService.addTrain(stationTrainCode, startStationTelecode, endStationTelecode, startTime, endTime, arriveDayDiff);
        return ResultEntity.data(b);
    }

    @RequestMapping("alterPassenger")
    public ResultEntity alterPassenger(@RequestParam int passengerId){
        return ResultEntity.data(passengerService.alterPassenger(passengerId));
    }

    @RequestMapping("allUserInfo")
    public ResultEntity allUserInfo(){
        return ResultEntity.data(userInfoService.allUserInfo());
    }

    @RequestMapping("deleteUser")
    public ResultEntity deleteUser(int userId){
        return ResultEntity.data(userInfoService.deleteUser(userId));
    }
}
