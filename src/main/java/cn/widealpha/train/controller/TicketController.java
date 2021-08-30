package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping("buyTicket")
    ResultEntity buyTicket(@RequestParam String stationTrainCode,
                           @RequestParam String startStationTelecode,
                           @RequestParam String endStationTelecode,
                           @RequestParam String seatTypeCode) {
        return ResultEntity.data(ticketService.buyTicket(startStationTelecode, endStationTelecode, stationTrainCode, seatTypeCode));
    }
}
