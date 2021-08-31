package cn.widealpha.train.service;

import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.domain.Passenger;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class PassengerService {
    @Autowired
    PassengerMapper passengerMapper;

    @Transient
    public StatusCode addPassenger(Passenger passenger) {
        if (UserUtil.getCurrentUserId() != null) {
            Integer passengerId = passengerMapper.existPassenger(passenger.getIdCardNo());
            if (passengerId == null) {
                if (passengerMapper.insertPassenger(passenger)){
                    passengerId = passenger.getPassengerId();
                } else {
                    return StatusCode.COMMON_FAIL;
                }
            } else {
                passenger.setPassengerId(passengerId);
            }
            passengerMapper.insertPassengerUserLink(UserUtil.getCurrentUserId(), passengerId);
            return StatusCode.SUCCESS;
        }
        return StatusCode.USER_NOT_LOGIN;
    }

    public StatusCode removePassenger(int passengerId){
        if (UserUtil.getCurrentUserId() != null) {
            if (passengerMapper.deletePassengerUserLink(passengerId, UserUtil.getCurrentUserId())){
                return StatusCode.SUCCESS;
            }
            return StatusCode.NO_DATA_EXIST;
        }
        return StatusCode.USER_NOT_LOGIN;
    }
}
