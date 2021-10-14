package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seatType")
public class SeatTypeController {
    @Autowired
    SeatTypeService seatTypeService;

    @RequestMapping("seatTypes")
    ResultEntity seatTypes(){
        return ResultEntity.data(seatTypeService.allSeatTypes());
    }
}
