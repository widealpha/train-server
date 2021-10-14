package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.util.StatusCode;
import cn.widealpha.train.pojo.entity.Passenger;
import cn.widealpha.train.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passenger")
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @RequestMapping("myPassengers")
    ResultEntity myPassengers() {
        return ResultEntity.data(passengerService.myPassengers());
    }

    @RequestMapping("passengerInfo")
    ResultEntity passengerInfo(@RequestParam int passengerId) {
        return ResultEntity.data(passengerService.passengerInfo(passengerId));
    }

    @RequestMapping("addPassenger")
    public ResultEntity addPassenger(@ModelAttribute Passenger passenger){
        passenger = passengerService.addPassenger(passenger);
        if (passenger == null){
            return ResultEntity.error(StatusCode.DATA_ALREADY_EXIST);
        }
        return ResultEntity.data(passenger);
    }

    @RequestMapping("removePassenger")
    public ResultEntity addPassenger(@RequestParam int passengerId){
        return ResultEntity.data(passengerService.removePassenger(passengerId));
    }
}
