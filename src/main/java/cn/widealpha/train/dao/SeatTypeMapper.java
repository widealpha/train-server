package cn.widealpha.train.dao;

import cn.widealpha.train.domain.SeatType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SeatTypeMapper {
    @Select("SELECT distinct * FROM seat_type")
    List<SeatType> selectSeatTypes();

    @Select("SELECT * FROM seat_type WHERE seat_type_code IN (SELECT distinct seat_type_code FROM coach WHERE station_train_code = #{stationTrainCode})")
    List<SeatType> selectSeatTypeByStationTrainCode(String stationTrainCode);

    @Insert("INSERT IGNORE INTO seat_type " +
            "(seat_type_code, seat_type_name) VALUES (#{seatTypeCode}, #{seatTypeName})")
    Boolean insertSeatType(SeatType seatType);

    @Update("UPDATE seat_type SET seat_type_name = #{seatTypeName} WHERE seat_type_code = #{seatTypeCode}")
    Boolean updateSeatTypeName(SeatType seatType);
}
