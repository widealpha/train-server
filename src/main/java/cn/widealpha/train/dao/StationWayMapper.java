package cn.widealpha.train.dao;

import cn.widealpha.train.domain.StationWay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationWayMapper {
    @Select("SELECT * FROM station_way WHERE coach_id IN (SELECT coach_id FROM coach WHERE station_train_code = #{stationTrainCode})")
    List<StationWay> selectStationWaysByStationTrainCode(String stationTrainCode);

    @Select("SELECT * FROM station_way WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode} AND coach_id = #{coachId}")
    StationWay selectStationWaysByKey(StationWay stationWay);

    @Select("SELECT * FROM station_way WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode}")
    List<StationWay> selectStationWayByStartEnd(String startStationTelecode, String endStationTelecode);

    @Update("CREATE TABLE #{date}(SELECT * FROM station_way)")
    Integer backupWays(String backupName);

    @Update("UPDATE station_way SET seat = #{seat} " +
            "WHERE start_station_telecode = #{startStationTelecode} " +
            "AND end_station_telecode = #{endStationTelecode} " +
            "AND coach_id = #{coachId}")
    boolean updateStationWaySeat(StationWay stationWay);

    @Insert("INSERT INTO station_way (start_station_telecode, end_station_telecode, coach_id, seat) " +
            "VALUES (#{startStationTelecode}, #{endStationTelecode}, #{coachId}, #{seat})")
    boolean insertStationWay(StationWay stationWay);

    @Delete("DELETE FROM station_way WHERE coach_id = #{coachId}")
    boolean deleteStationWayByCoachId(int coachId);
}
