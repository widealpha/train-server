package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.util.StatusCode;
import cn.widealpha.train.service.TicketService;
import cn.widealpha.train.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

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
                           @RequestParam String date,
                           Character preferSeat) {
        return ticketService.buyTicket(startStationTelecode, endStationTelecode, stationTrainCode, seatTypeCode, passengerId, student, date, preferSeat);
    }

    @RequestMapping("cancelTicket")
    ResultEntity cancelTicket(@RequestParam int ticketId) {
        return ResultEntity.data(ticketService.cancelTicket(ticketId));
    }

    @RequestMapping("changeTicket")
    ResultEntity changeTicket(@RequestParam int ticketId, @RequestParam String stationTrainCode) {
        return ticketService.changeTicket(ticketId, stationTrainCode);
    }

    @RequestMapping("userTickets")
    ResultEntity userTickets() {
        return ResultEntity.data(ticketService.userTickets());
    }

    @RequestMapping("selfTickets")
    ResultEntity selfTickets() {
        return ResultEntity.data(ticketService.selfTickets());
    }

    @RequestMapping("passengerTickets")
    ResultEntity passengerTickets(@RequestParam int passengerId) {
        return ResultEntity.data(ticketService.passengerTicket(passengerId));
    }

    @RequestMapping("ticketInfoByOrder")
    ResultEntity ticketInfoByOrder(@RequestParam int orderId) {
        return ResultEntity.data(ticketService.ticketInfoByOrder(orderId));
    }

    @RequestMapping("transferSeatBigInteger")
    ResultEntity transferSeatBigInteger(String bigInteger, String seatName) {
        if (StringUtil.allEmpty(bigInteger, seatName)) {
            return ResultEntity.error(StatusCode.COMMON_FAIL);
        }
        if (StringUtil.isEmpty(bigInteger)) {
            char c = seatName.charAt(seatName.length() - 1);
            int r = c - 'A';
            int i = Integer.parseInt(seatName.substring(0, seatName.length() - 1)) - 1;
            BigInteger big = BigInteger.ZERO;
            big = big.setBit(i * 4 + r);
            return ResultEntity.data(big.toString());
        } else {
            BigInteger big = new BigInteger(bigInteger);
            int i = big.getLowestSetBit();
            return ResultEntity.data("" + (i / 4 + 1) + (char) (i % 4 + 'A'));
        }
    }
}
