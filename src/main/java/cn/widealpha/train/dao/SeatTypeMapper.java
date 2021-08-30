package cn.widealpha.train.dao;

import cn.widealpha.train.domain.SeatType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SeatTypeMapper {
    @Select("SELECT distinct * FROM seat_type")
    List<SeatType> selectSeatTypes();

    @Select("SELECT * FROM seat_type WHERE seat_type_code IN (SELECT distinct seat_type_code FROM coach WHERE station_train_code = #{stationTrainCode})")
    List<SeatType> selectSeatTypeByStationTrainCode(String stationTrainCode);
}
