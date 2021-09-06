package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Coach;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CoachMapper {
    @Select("SELECT * FROM coach WHERE coach_id = #{coachId}")
    Coach selectCoachByCoachId(int coachId);

    @Select("SELECT * FROM coach WHERE station_train_code = #{stationTrainCode}")
    List<Coach> selectCoachByStationTrainCode(String stationTrainCode);

    @Select("SELECT * FROM coach WHERE station_train_code = #{stationTrainCode} AND seat_type_code = #{seatTypeCode}")
    List<Coach> selectCoachByStationTrainCodeAndSeatType(String stationTrainCode, String seatTypeCode);

    @Options(useGeneratedKeys = true, keyProperty = "coachId")
    @Insert("INSERT INTO coach (coach_no, station_train_code, seat_type_code, seat) VALUES (#{coachNo}, #{stationTrainCode}, #{seatTypeCode}, #{seat})")
    Integer insertCoach(Coach coach);

    @Delete("UPDATE coach SET seat = #{seat}, seat_type_code = #{seatTypeCode} WHERE coach_id = #{coachId}")
    boolean updateCoachByCoachId(int coachId, String seatTypeCode, BigInteger seat);

    @Delete("DELETE FROM coach WHERE station_train_code = #{stationTrainCode}")
    Integer deleteCoachByStationTrainCode(String stationTrainCode);

    @Delete("DELETE FROM coach WHERE coach_id = #{coachId}")
    boolean deleteCoachByCoachId(int coachId);
}
