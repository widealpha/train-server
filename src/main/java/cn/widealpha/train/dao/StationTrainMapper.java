package cn.widealpha.train.dao;

import cn.widealpha.train.domain.StationTrain;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationTrainMapper {
    @Select("SELECT * FROM station_train WHERE station_train_code = #{stationTrainCode} AND station_no = #{stationNo} AND station_telecode = #{stationTelecode}")
    StationTrain selectStationTrainByKey(String stationTrainCode, String stationTelecode, int stationNo);

    @Select("SELECT * FROM station_train WHERE station_train_code = #{stationTrainCode} ORDER BY station_no")
    List<StationTrain> selectStationTrainByStationTrainCode(String stationTrainCode);

    @Select("SELECT * FROM station_train WHERE station_no BETWEEN " +
            "(SELECT station_no FROM station_train WHERE station_train_code = #{stationTrainCode} AND station_telecode = #{startStationTelecode}) AND " +
            "(SELECT station_no FROM station_train WHERE station_train_code = #{stationTrainCode} AND station_telecode = #{endStationTelecode}) " +
            "AND station_train_code = #{stationTrainCode} ORDER BY station_no")
    List<StationTrain> selectStationTrainByStartEndCode(String startStationTelecode, String endStationTelecode, String stationTrainCode);

    @Select("SELECT t1.station_telecode as station_telecode FROM (SELECT distinct station_telecode, station_no FROM station_train WHERE station_train_code = #{stationTrainCode} ORDER BY station_no) t1")
    List<String> selectTelecodeByStationTrain(String stationTrainCode);

    @Select("SELECT station_telecode FROM station_train WHERE station_train_code = #{stationTrainCode} " +
            "AND station_no > (SELECT station_no FROM station_train " +
            "WHERE station_train_code = #{stationTrainCode} AND station_telecode = #{startStationTelecode}) " +
            "ORDER BY station_no")
    List<String> selectTelecodeByStationTrainAfter(String stationTrainCode, String startStationTelecode);

    @Select("SELECT t1.station_train_code FROM station_train t1, station_train t2 " +
            "WHERE t1.station_telecode = #{startStationTelecode} " +
            "AND t2.station_telecode = #{endStationTelecode} " +
            "AND t1.station_train_code = t2.station_train_code " +
            "AND t1.station_no < t2.station_no")
    List<String> selectStationTrainCodeByStartEnd(String startStationTelecode, String endStationTelecode);

    @Select("SELECT t1.station_train_code, t1.station_telecode, t1.arrive_day_diff, t1.arrive_time, t1.update_arrive_time, t1.start_time, t1.update_start_time, t1.start_day_diff, t1.station_no " +
            "FROM station_train t1, station_train t2 " +
            "WHERE t1.station_telecode = #{startStationTelecode} " +
            "AND t2.station_telecode = #{endStationTelecode} " +
            "AND t1.station_train_code = t2.station_train_code " +
            "AND t1.station_no < t2.station_no")
    List<StationTrain> selectStationTrainByStartEnd(String startStationTelecode, String endStationTelecode);

    @Select("SELECT distinct station_train_code FROM station_train WHERE station_telecode = #{stationTelecode}")
    List<String> selectStationTrainCodeByStationTelecode(String telecode);

    @Select("SELECT * FROM station_train WHERE station_train_code = #{stationTrainCodes} AND station_telecode in (#{stationTelecode})")
    List<StationTrain> selectStationTrainByStationTrainCodeAndStationCode(List<String> stationTrainCodes, String stationTelecode);

    @Insert("INSERT INTO station_train (station_train_code, station_telecode, arrive_day_diff, arrive_time, " +
            "update_arrive_time, start_time, update_start_time, start_day_diff, station_no) " +
            "VALUES (#{stationTrainCode}, #{stationTelecode}, #{arriveDayDiff}, #{arriveTime}, " +
            "#{updateArriveTime}, #{startTime}, #{updateStartTime}, #{startDayDiff}, #{stationNo})")
    boolean insertStationTrain(StationTrain stationTrain);

    @Update("UPDATE station_train " +
            "SET arrive_day_diff = #{arriveDayDiff}, arrive_time = #{arriveTime}, " +
            "update_arrive_time = #{updateArriveTime}, start_time = #{startTime}, " +
            "update_start_time = #{updateStartTime}, start_day_diff = #{startDayDiff} " +
            "WHERE station_telecode = #{stationTelecode} AND station_telecode = #{stationTelecode} AND station_no = #{stationNo}")
    boolean updateStationTrain(StationTrain stationTrain);

    @Update("UPDATE station_train " +
            "SET station_telecode = #{updateStationTrainTelecode} " +
            "WHERE station_telecode = #{stationTelecode} AND station_telecode = #{stationTelecode} AND station_no = #{stationNo}")
    boolean updateStationTrainTelecode(String stationTrainCode, String stationTelecode, int stationNo, String updateStationTrainTelecode);

    @Select("SELECT station_no FROM station_train WHERE station_train_code = #{stationTrainCode} ORDER BY station_no")
    List<Integer> selectStationNos(String stationTrainCode);

    @Delete("DELETE FROM station_train WHERE station_train_code = #{stationTrainCode}")
    Integer deleteStationTrainByStationTrainCode(String stationTrainCode);

    @Delete("DELETE FROM station_train WHERE station_train_code = #{stationTrainCode} AND station_telecode = #{stationTelecode}")
    Integer deleteStationTrainByKey(String stationTrainCode, String stationTelecode);
}
