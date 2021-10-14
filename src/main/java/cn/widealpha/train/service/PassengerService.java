package cn.widealpha.train.service;

import cn.widealpha.train.util.StatusCode;
import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.dao.UserInfoMapper;
import cn.widealpha.train.pojo.entity.Passenger;
import cn.widealpha.train.pojo.entity.UserInfo;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    PassengerMapper passengerMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Transactional
    public Passenger addPassenger(Passenger passenger) {
        if (UserUtil.getCurrentUserId() != null) {
            Integer passengerId = passengerMapper.existPassenger(passenger.getIdCardNo());
            if (passengerId == null) {
                if (passengerMapper.insertPassenger(passenger)) {
                    passengerId = passenger.getPassengerId();
                } else {
                    return null;
                }
            } else {
                Passenger p = passengerMapper.selectPassengersByPassengerId(passengerId);
                passenger.setPassengerId(passengerId);
                if (!p.getVerified()){
                    return null;
                }
            }
            passengerMapper.insertPassengerUserLink(passengerId, UserUtil.getCurrentUserId());
            return passenger;
        }
        return null;
    }

    public StatusCode removePassenger(int passengerId) {
        if (UserUtil.getCurrentUserId() != null) {
            if (passengerMapper.deletePassengerUserLink(passengerId, UserUtil.getCurrentUserId())) {
                return StatusCode.SUCCESS;
            }
            return StatusCode.NO_DATA_EXIST;
        }
        return StatusCode.USER_NOT_LOGIN;
    }

    public List<Passenger> myPassengers() {
        if (UserUtil.getCurrentUserId() != null) {
            UserInfo userInfo = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
            List<Passenger> passengers = passengerMapper.selectPassengersByUserId(UserUtil.getCurrentUserId());
            if (userInfo!= null && userInfo.getSelfPassengerId() != null){
                passengers.add(passengerMapper.selectPassengersByPassengerId(userInfo.getSelfPassengerId()));
            }
            return passengers;
        }
        return new ArrayList<>();
    }

    public Passenger passengerInfo(int passengerId) {
        if (UserUtil.getCurrentUserId() != null) {
            return passengerMapper.selectPassengersByPassengerIdAndUserId(passengerId, UserUtil.getCurrentUserId());
        }
        return null;
    }

    public List<Passenger> allPassengers(){
        return passengerMapper.selectAllPassengers();
    }

    @Transactional
    public boolean alterPassenger(int passengerId){
        Passenger passenger= passengerMapper.selectPassengersByPassengerId(passengerId);
        passenger.setVerified(!passenger.getVerified());
        passengerMapper.updatePassenger(passenger);
        return passengerMapper.deletePassengerLink(passengerId);
    }
}
