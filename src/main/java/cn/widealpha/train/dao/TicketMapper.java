package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TicketMapper {
    @Options(useGeneratedKeys = true,keyProperty = "ticketId")
    @Insert("INSERT INTO " +
            "ticket (coach_id, seat, station_train_code, start_station_telecode, end_station_telecode, start_time, end_time, price, passenger_id, student, order_id) " +
            "VALUES (#{coachId}, #{seat}, #{stationTrainCode}, #{startStationTelecode}, #{endStationTelecode}, #{startTime}, #{endTime}, #{price}, #{passengerId}, #{student}, #{orderId})")
    Integer insertTicket(Ticket ticket);

    @Update("UPDATE ticket SET order_id = #{orderId} WHERE ticket_id = #{ticketId}")
    Integer insertTicketOrderLink(int ticketId, int orderId);

    @Delete("DELETE FROM ticket WHERE ticket_id = #{ticketId}")
    Integer deleteTicker(int ticketId);

    @Select("SELECT * FROM ticket WHERE passenger_id = #{passengerId}")
    List<Ticket> selectTicketByPassengerId(int passengerId);

    @Select("SELECT * FROM ticket WHERE passenger_id = #{passengerId} AND order_id in (SELECT order_id FROM order_form WHERE payed = 1)")
    List<Ticket> selectTicketByPassengerIdEnsurePayed(int passengerId);

    @Select("SELECT * FROM ticket WHERE order_id IN (SELECT order_form.order_id FROM order_form WHERE user_id = #{userId})")
    List<Ticket> selectTicketByUserId(int userId);

    @Select("SELECT * FROM ticket WHERE order_id = #{orderId}")
    List<Ticket> selectTicketByOrderFormId(int orderId);

    @Select("SELECT * FROM ticket WHERE ticket_id = #{ticketId} AND order_id IN (SELECT order_id FROM order_form WHERE user_id = #{userId})")
    boolean ticketBelongToUserId(int ticketId, int userId);

    @Select("SELECT * FROM ticket WHERE ticket_id = #{ticketId}")
    Ticket selectTicketByTicketId(int ticketId);

}
