package cn.widealpha.train.service;

import cn.widealpha.train.dao.TrainClassMapper;
import cn.widealpha.train.domain.TrainClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainClassService {
    @Autowired
    TrainClassMapper trainClassMapper;

    public List<TrainClass> allTrainClass() {
        return trainClassMapper.selectAllTrainClass();
    }

    public boolean renameTrainClass(TrainClass trainClass) {
        return trainClassMapper.updateTrainClassName(trainClass);
    }

    public boolean addTrainClass(TrainClass trainClass) {
        return trainClassMapper.insertTrainClass(trainClass);
    }
}
