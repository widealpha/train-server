package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.service.TrainClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trainClass")
public class TrainClassController {
    @Autowired
    TrainClassService trainClassService;

    @RequestMapping("trainClass")
    ResultEntity trainClass(){
        return ResultEntity.data(trainClassService.allTrainClass());
    }
}
