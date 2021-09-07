package cn.widealpha.train.service;

import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.PassengerMapper;
import cn.widealpha.train.domain.Passenger;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    PassengerMapper passengerMapper;

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
                passenger.setPassengerId(passengerId);
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
            return passengerMapper.selectPassengersByUserId(UserUtil.getCurrentUserId());
        }
        return new ArrayList<>();
    }

    public Passenger passengerInfo(int passengerId) {
        if (UserUtil.getCurrentUserId() != null) {
            return passengerMapper.selectPassengersByPassengerIdAndUserId(passengerId, UserUtil.getCurrentUserId());
        }
        return null;
    }
}
