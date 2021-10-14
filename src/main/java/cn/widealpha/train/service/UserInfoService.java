package cn.widealpha.train.service;

import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.dao.UserInfoMapper;
import cn.widealpha.train.pojo.entity.Passenger;
import cn.widealpha.train.pojo.entity.UserInfo;
import cn.widealpha.train.util.ObjectUtil;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    PassengerMapper passengerMapper;

    public UserInfo getUserInfo() {
        if (UserUtil.getCurrentUserId() != null) {
            UserInfo userInfo = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
            if (userInfo.getSelfPassengerId() != null){
                userInfo.setIdCardNo(passengerMapper.selectPassengersByPassengerId(userInfo.getSelfPassengerId()).getIdCardNo());
            }
            return userInfo;
        }
        return null;
    }

    @Transactional
    public boolean updateUserInfo(UserInfo userInfo) {
        if (userInfo.getIdCardNo() != null && userInfo.getRealName() != null && userInfo.getPhone() != null){
            Integer passengerId = passengerMapper.existPassenger(userInfo.getIdCardNo());
            if (passengerId != null){
                Passenger passenger = passengerMapper.selectPassengersByPassengerId(passengerId);
                if (Objects.equals(passenger.getName(), userInfo.getRealName())){
                    userInfo.setSelfPassengerId(passengerId);
                    try {
                        passengerMapper.insertPassengerUserLink(passengerId, UserUtil.getCurrentUserId());
                    } catch (Exception ignore) {}
                }
            } else {
                Passenger passenger = new Passenger();
                passenger.setName(userInfo.getRealName());
                passenger.setIdCardNo(userInfo.getIdCardNo());
                passenger.setPhone(userInfo.getPhone());
                passenger.setStudent(false);
                if (passengerMapper.insertPassenger(passenger)){
                    userInfo.setSelfPassengerId(passenger.getPassengerId());
                    passengerMapper.insertPassengerUserLink(passenger.getPassengerId(), UserUtil.getCurrentUserId());
                }
            }
        }
        if (UserUtil.getCurrentUserId() != null) {
            UserInfo info = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
            ObjectUtil.copyOnNotNull(userInfo, info);
            return userInfoMapper.updateUserInfo(info);
        }
        return false;
    }

    public List<UserInfo> allUserInfo(){
        return userInfoMapper.selectAllUserInfo();
    }

    public boolean deleteUser(int userId){
        return userInfoMapper.deleteUser(userId);
    }
}
