package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Passenger;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PassengerMapper {
    @Select("SELECT * FROM passenger WHERE passenger_id IN (SELECT passenger_id FROM user_passenger WHERE user_id = #{userId})")
    List<Passenger> selectPassengersByUserId(int userId);

    @Select("SELECT * FROM passenger WHERE passenger_id = #{passengerId} AND passenger_id IN (SELECT passenger_id FROM user_passenger WHERE user_id = #{userId})")
    Passenger selectPassengersByPassengerIdAndUserId(int passengerId, int userId);

    @Select("SELECT * FROM passenger WHERE passenger_id = #{passengerId}")
    Passenger selectPassengersByPassengerId(int passengerId);

    @Select("SELECT passenger_id FROM passenger WHERE id_card_no = #{idCardNo}")
    Integer existPassenger(String idCardNo);

    @Options(useGeneratedKeys = true,keyProperty = "passengerId")
    @Insert("INSERT INTO passenger (id_card_no, student, name, phone) VALUES (#{idCardNo}, #{student}, #{name}, #{phone})")
    boolean insertPassenger(Passenger passenger);

    @Insert("INSERT INTO user_passenger (passenger_id, user_id) VALUES (#{passengerId}, #{userId})")
    boolean insertPassengerUserLink(int passengerId, int userId);

    @Delete("DELETE FROM user_passenger WHERE user_id = #{userId} AND passenger_id = #{passengerId}")
    boolean deletePassengerUserLink(int passengerId, int userId);
}
