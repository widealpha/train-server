package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.pojo.entity.Passenger;
import cn.widealpha.train.pojo.entity.UserInfo;
import cn.widealpha.train.service.PassengerService;
import cn.widealpha.train.service.UserInfoService;
import cn.widealpha.train.util.FileUtil;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResultEntity uploadInfo(@RequestBody UserInfo userInfo){
        return ResultEntity.data(userInfoService.updateUserInfo(userInfo));
    }


    @RequestMapping("uploadImage")
    public ResultEntity uploadHeadImage(@RequestParam MultipartFile image){
        return ResultEntity.data(FileUtil.saveImage(image, "train"));
    }

    @RequestMapping("realName")
    @Transactional
    public ResultEntity realName(@ModelAttribute Passenger passenger){
        passengerService.addPassenger(passenger);
        UserInfo userInfo = userInfoService.getUserInfo();
        userInfo.setSelfPassengerId(passenger.getPassengerId());
        return ResultEntity.data(userInfoService.updateUserInfo(userInfo));
    }

    @RequestMapping("isAdmin")
    public ResultEntity isAdmin(){
        return ResultEntity.data(UserUtil.hasRole("ROLE_ADMIN") || UserUtil.hasRole("ROLE_SYSTEM")) ;
    }

}
