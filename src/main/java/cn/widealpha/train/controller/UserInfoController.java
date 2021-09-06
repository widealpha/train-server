package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.domain.Passenger;
import cn.widealpha.train.domain.UserInfo;
import cn.widealpha.train.service.PassengerService;
import cn.widealpha.train.service.UserInfoService;
import cn.widealpha.train.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("updateInfo")
    public ResultEntity uploadInfo(@ModelAttribute UserInfo userInfo){
        return ResultEntity.data(userInfoService.updateUserInfo(userInfo));
    }


    @RequestMapping("uploadImage")
    public ResultEntity uploadHeadImage(@RequestBody MultipartFile multipartFile){
        return ResultEntity.data(FileUtil.saveImage(multipartFile, "train"));
    }

    @RequestMapping("realName")
    @Transient
    public ResultEntity realName(@ModelAttribute Passenger passenger){
        passengerService.addPassenger(passenger);
        UserInfo userInfo = userInfoService.getUserInfo();
        userInfo.setSelfPassengerId(passenger.getPassengerId());
        return ResultEntity.data(userInfoService.updateUserInfo(userInfo));
    }
}
