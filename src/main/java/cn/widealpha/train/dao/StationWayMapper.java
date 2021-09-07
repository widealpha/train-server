package cn.widealpha.train.dao;

import cn.widealpha.train.domain.StationWay;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface StationWayMapper {
    @Select("SELECT * FROM station_way_base WHERE coach_id IN (SELECT coach_id FROM coach WHERE station_train_code = #{stationTrainCode})")
    List<StationWay> selectStationWaysByStationTrainCode(String stationTrainCode);

    @Update("UPDATE station_way_base SET seat = #{seat} WHERE coach_id = #{coachId}")
    Integer updateStationWayBaseByCoachId(BigInteger seat, int coachId);

    @Select("SELECT * FROM station_way " +
            "WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode} AND coach_id = #{coachId} AND date = #{date}")
    StationWay selectStationWaysByKey(StationWay stationWay);

    @Select("SELECT * FROM station_way WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode} AND date = #{date}")
    List<StationWay> selectStationWayByStartEnd(String startStationTelecode, String endStationTelecode, String date);

    @Insert("INSERT INTO station_way SELECT start_station_telecode, end_station_telecode, coach_id, seat, #{date} FROM station_way_base")
    Integer copyStationBase(String date);

    @Update("UPDATE station_way SET seat = #{seat} " +
            "WHERE start_station_telecode = #{startStationTelecode} " +
            "AND end_station_telecode = #{endStationTelecode} " +
            "AND coach_id = #{coachId} " +
            "AND date = #{date}")
    boolean updateStationWaySeat(StationWay stationWay);

    @Insert("INSERT INTO station_way_base (start_station_telecode, end_station_telecode, coach_id, seat) " +
            "VALUES (#{startStationTelecode}, #{endStationTelecode}, #{coachId}, #{seat})")
    boolean insertStationWay(StationWay stationWay);

    @Delete("DELETE FROM station_way_base WHERE coach_id = #{coachId}")
    boolean deleteStationWayByCoachId(int coachId);

    @Delete("DELETE FROM station_way_base WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode} AND coach_id IN (SELECT coach_id FROM coach WHERE station_train_code = #{stationTrainCode})")
    boolean deleteStationWayByKeyAndStationTrainCode(String startStationTelecode, String endStationTelecode, String stationTrainCode);
}
