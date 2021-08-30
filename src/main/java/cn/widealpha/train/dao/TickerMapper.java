package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TickerMapper {
    @Options(useGeneratedKeys = true,keyProperty = "tickerId")
    @Insert("INSERT INTO " +
            "ticket (coach_id, seat, station_train_code, start_station_telecode, end_station_telecode, start_time, end_time, price, passenger_id) " +
            "VALUES (#{coachId}, #{seat}, #{stationTrainCode}, #{startStationTelecode}, #{endStationTelecode}, #{startTime}, #{endTime}, #{price}, #{passengerId})")
    Integer addTicket(Ticket ticket);

    @Insert("INSERT INTO order_form (user_id, ticket_id, payed) VALUES (#{userId}, #{ticketId}, false)")
    Integer addTicketUserLink(int tickerId, int userId);

    @Update("UPDATE order_form SET payed = #{payed} WHERE user_id = #{userId} AND ticket_id = #{ticketId}")
    Integer updateTicketUserLink(int userId, int ticketId, boolean payed);

    @Delete("DELETE FROM ticket WHERE ticket_id = #{ticketId}")
    Integer deleteTicker(int ticketId);

    @Select("SELECT * FROM ticket WHERE passenger_id = #{passengerId}")
    List<Ticket> selectTicketByPassengerId(int passengerId);

    @Select("SELECT * FROM ticket WHERE ticket_id IN (SELECT ticket_id FROM order_form WHERE user_id = #{userId})")
    List<Ticket> selectTicketByUserId(int userId);


}
