package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Passenger;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PassengerMapper {
    @Select("SELECT * FROM passenger WHERE passenger_id IN (SELECT * FROM user_passenger WHERE user_id = #{userId})")
    List<Passenger> selectPassengersByUserId(int userId);

    @Options(useGeneratedKeys = true,keyProperty = "passenger_id")
    @Insert("INSERT INTO passenger (id_card_no, student) VALUES (#{idCardNo}, #{student})")
    boolean insertPassenger(Passenger passenger);

    @Insert("INSERT INTO user_passenger (passenger_id, user_id) VALUES (#{passengerId}, #{userId})")
    boolean insertPassengerUserLink(int passengerId, int userId);

    @Delete("DELETE FROM user_passenger WHERE user_id = #{userId} AND passenger_id = #{passengerId}")
    boolean deletePassengerUserLink(int passengerId, int userId);
}
