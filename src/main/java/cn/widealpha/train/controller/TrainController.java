package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("train")
public class TrainController {
    @Autowired
    TrainService trainService;

    @RequestMapping("allTrains")
    ResultEntity allTrains(@RequestParam Integer page, @RequestParam Integer size) {
        if (page == null || size == null || page == 0 || size == 0) {
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(trainService.getTrains(page, size));
    }

    @RequestMapping("trainInfo")
    ResultEntity trainInfo(@RequestParam String stationTrainCode){
        if (stationTrainCode == null){
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
        return ResultEntity.data(trainService.getTrainByName(stationTrainCode));
    }
}
