package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Coach;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoachMapper {
    @Select("SELECT * FROM coach WHERE station_train_code = #{stationTrainCode}")
    List<Coach> selectCoachByStationTrainCode(String stationTrainCode);

    @Select("SELECT * FROM coach WHERE station_train_code = #{stationTrainCode} AND seat_type_code = #{seatTypeCode}")
    List<Coach> selectCoachByStationTrainCodeAndSeatType(String stationTrainCode, String seatTypeCode);

    @Insert("INSERT INTO coach (coach_no, station_train_code, seat_type_code, seat) VALUES #{coaches}")
    Integer insertCoaches(List<Coach> coaches);

    @Delete("DELETE FROM coach WHERE station_train_code = #{stationTrainCode}")
    Integer deleteCoachByStationTrainCode(String stationTrainCode);
}
