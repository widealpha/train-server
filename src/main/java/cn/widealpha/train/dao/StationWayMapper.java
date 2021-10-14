package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.StationWay;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

/**
 * 这个类会同时操纵station_way_base和station_way表
 * 所有更新操作会先反映在station_way_base表上,第二天统一更新添加到station_way中
 * 详见 {@link cn.widealpha.train.service.ScheduleService#copyStationWayBase}
 */
@Mapper
public interface StationWayMapper {
    @Select("SELECT * FROM station_way_base WHERE coach_id IN (SELECT coach_id FROM coach WHERE train_code = #{trainCode})")
    List<StationWay> selectStationWaysByTrainCode(String trainCode);

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

    @Delete("DELETE FROM station_way_base WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode} AND coach_id IN (SELECT coach_id FROM coach WHERE train_code = #{stationTrainCode})")
    boolean deleteStationWayByKeyAndStationTrainCode(String startStationTelecode, String endStationTelecode, String stationTrainCode);
}
