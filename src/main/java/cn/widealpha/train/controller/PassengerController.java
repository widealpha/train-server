package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
