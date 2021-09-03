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

    @RequestMapping("ticketInfo")
    ResultEntity ticketInfo(@RequestParam int ticketId) {
        return ResultEntity.data(ticketService.ticketInfo(ticketId));
    }

    @RequestMapping("buyTicket")
    ResultEntity buyTicket(@RequestParam String stationTrainCode,
                           @RequestParam String startStationTelecode,
                           @RequestParam String endStationTelecode,
                           @RequestParam String seatTypeCode,
                           @RequestParam int passengerId,
                           @RequestParam boolean student,
                           @RequestParam String date) {
        return ticketService.buyTicket(startStationTelecode, endStationTelecode, stationTrainCode, seatTypeCode, passengerId, student, date);
    }

    @RequestMapping("cancelTicket")
    ResultEntity cancelTicket(@RequestParam int ticketId) {
        return ResultEntity.data(ticketService.cancelTicket(ticketId));
    }

    @RequestMapping("changeTicket")
    ResultEntity changeTicket(@RequestParam int ticketId, @RequestParam String stationTrainCode) {
        return ResultEntity.data(ticketService.changeTicket(ticketId, stationTrainCode));
    }

    @RequestMapping("userTickets")
    ResultEntity userTickets() {
        return ResultEntity.data(ticketService.userTickets());
    }

    @RequestMapping("passengerTickets")
    ResultEntity passengerTickets(int passengerId) {
        return ResultEntity.data(ticketService.passengerTicket(passengerId));
    }
}
