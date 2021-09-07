package cn.widealpha.train.service;

import cn.widealpha.train.dao.StationPriceMapper;
import cn.widealpha.train.dao.TrainClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PriceService {
    @Autowired
    StationPriceMapper stationPriceMapper;

    @Autowired
    TrainClassMapper trainClassMapper;

    @Transactional
    public boolean updateTrainClassPriceRatio(String trainClassCode, double ratio){
        return stationPriceMapper.updateTrainClassPriceRatio(trainClassCode, ratio) > 0;
    }

    @Transactional
    public boolean updateSeatPriceRatio(String seatTypeCode, double ratio){
        return stationPriceMapper.updateSeatPriceRatio(seatTypeCode, ratio) > 0;
    }

    public  boolean updateStationPriceRatio(String startStationTelecode, String endStationTelecode, double ratio){
        return stationPriceMapper.updateStationPriceRatio(startStationTelecode, endStationTelecode,ratio) > 0;
    }
}
