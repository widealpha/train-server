package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.domain.Passenger;
import cn.widealpha.train.domain.UserInfo;
import cn.widealpha.train.service.PassengerService;
import cn.widealpha.train.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PassengerService passengerService;

    @RequestMapping("myInfo")
    public ResultEntity myUserInfo() {
        return ResultEntity.data(userInfoService.getUserInfo());
    }

    @RequestMapping("realName")
    @Transient
    public ResultEntity realName(@ModelAttribute Passenger passenger){
        passengerService.addPassenger(passenger);
        UserInfo userInfo = userInfoService.getUserInfo();
        userInfo.setSelfPassengerId(passenger.getPassengerId());
        return ResultEntity.data(userInfoService.updateUserInfo(userInfo));
    }

    @RequestMapping("addPassenger")
    public ResultEntity addPassenger(@ModelAttribute Passenger passenger){
        return ResultEntity.data(passengerService.addPassenger(passenger));
    }
}
