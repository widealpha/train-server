package cn.widealpha.train.service;

import cn.widealpha.train.dao.SeatTypeMapper;
import cn.widealpha.train.pojo.entity.SeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatTypeService {
    @Autowired
    SeatTypeMapper seatTypeMapper;

    public List<SeatType> allSeatTypes() {
        return seatTypeMapper.selectSeatTypes();
    }

    public boolean renameSeatType(SeatType seatType) {
        return seatTypeMapper.updateSeatTypeName(seatType);
    }

    public boolean addSeatType(SeatType seatType) {
        return seatTypeMapper.insertSeatType(seatType);
    }
}
