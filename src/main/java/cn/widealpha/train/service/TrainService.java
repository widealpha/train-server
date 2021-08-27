package cn.widealpha.train.service;

import cn.widealpha.train.bean.Pager;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.TrainMapper;
import cn.widealpha.train.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {
    @Autowired
    TrainMapper trainMapper;

    public Pager<Train> getTrains(int page, int size) {
        Pager<Train> pager = new Pager<>();
        Integer count = trainMapper.count();
        if ((page - 1) * size >= count) {
            pager.setSize(size);
            pager.setPage(count / size);
            pager.setTotal(count);
            pager.setRows(new ArrayList<>());
            return pager;
        }
        pager.setSize(size);
        pager.setPage(page);
        pager.setTotal(count);
        pager.setRows(trainMapper.selectTrains(page, size));
        return pager;
    }

    public Train getTrainByName(String stationTrainCode){
        return trainMapper.selectTrainByStationTrainCode(stationTrainCode);
    }

    public StatusCode addTrain(Train train){
        return StatusCode.SUCCESS;
    }
}
