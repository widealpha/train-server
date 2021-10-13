package cn.widealpha.train.dao;

import cn.widealpha.train.domain.StationTrain;
import cn.widealpha.train.util.MybatisExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationTrainMapper {
    @Select("SELECT * FROM station_train WHERE train_code = #{stationTrainCode} AND station_no = #{stationNo} AND station_telecode = #{stationTelecode}")
    StationTrain selectStationTrainByKey(String stationTrainCode, String stationTelecode, int stationNo);

    @Select("SELECT * FROM station_train WHERE train_code = #{stationTrainCode} ORDER BY station_no")
    List<StationTrain> selectStationTrainByStationTrainCode(String stationTrainCode);

    @Select("SELECT * FROM station_train WHERE station_no BETWEEN " +
            "(SELECT station_no FROM station_train WHERE train_code = #{stationTrainCode} AND station_telecode = #{startStationTelecode}) AND " +
            "(SELECT station_no FROM station_train WHERE train_code = #{stationTrainCode} AND station_telecode = #{endStationTelecode}) " +
            "AND train_code = #{stationTrainCode} ORDER BY station_no")
    List<StationTrain> selectStationTrainByStartEndCode(String startStationTelecode, String endStationTelecode, String stationTrainCode);

    @Select("SELECT t1.station_telecode as station_telecode FROM (SELECT distinct station_telecode, station_no FROM station_train WHERE train_code = #{stationTrainCode} ORDER BY station_no) t1")
    List<String> selectTelecodeByStationTrain(String stationTrainCode);

    @Select("SELECT station_telecode FROM station_train WHERE train_code = #{trainCode} " +
            "AND station_no > (SELECT station_no FROM station_train " +
            "WHERE train_code = #{trainCode} AND station_telecode = #{startStationTelecode}) " +
            "ORDER BY station_no")
    List<String> selectTelecodeByTrainAfter(String trainCode, String startStationTelecode);


    /**
     * 获取经过两个站台之间的列车号
     * @param startStationTelecode 起始站台
     * @param endStationTelecode 终止站台
     * @return 列车号
     */
    @Select("SELECT t1.train_code FROM station_train t1, station_train t2 " +
            "WHERE t1.station_telecode = #{startStationTelecode} " +
            "AND t2.station_telecode = #{endStationTelecode} " +
            "AND t1.train_code = t2.train_code " +
            "AND t1.station_no < t2.station_no")
    List<String> selectTrainCodeByStartEnd(String startStationTelecode, String endStationTelecode);

    /**
     * 获取经过两个站台之间的列车的详细信息
     * @param startStationTelecode 起始站台
     * @param endStationTelecode 终止站台
     * @return 列车到站详细信息
     */
    @Select("SELECT t1.train_code, t1.station_telecode, t1.arrive_day_diff, t1.arrive_time, t1.update_arrive_time, t1.start_time, t1.update_start_time, t1.start_day_diff, t1.station_no " +
            "FROM station_train t1, station_train t2 " +
            "WHERE t1.station_telecode = #{startStationTelecode} " +
            "AND t2.station_telecode = #{endStationTelecode} " +
            "AND t1.train_code = t2.train_code " +
            "AND t1.station_no < t2.station_no")
    List<StationTrain> selectStationTrainByStartEnd(String startStationTelecode, String endStationTelecode);

    /**
     * 获取经过站台的列车号
     * @param telecode 站台电报码
     * @return 经过站台的列车号
     */
    @Select("SELECT distinct train_code FROM station_train WHERE station_telecode = #{stationTelecode}")
    List<String> selectTrainCodeByStationTelecode(String telecode);

    /**
     * 获取多辆列车的到站信息
     * @param stationTrainCodes 开始的列车信息
     * @param stationTelecode 经过的站台号
     * @return 多辆列车经过此站台的信息
     */
    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM station_train WHERE train_code = #{stationTrainCodes} AND station_telecode in (#{stationTelecode})")
    List<StationTrain> selectStationTrainByStationTrainCodeAndStationCode(List<String> stationTrainCodes, String stationTelecode);

    /**
     * 插入车站与站台信息
     * @param stationTrain 信息
     * @return true/false
     */
    @Insert("INSERT INTO station_train (train_code, station_telecode, arrive_day_diff, arrive_time, " +
            "update_arrive_time, start_time, update_start_time, start_day_diff, station_no) " +
            "VALUES (#{trainCode}, #{stationTelecode}, #{arriveDayDiff}, #{arriveTime}, " +
            "#{updateArriveTime}, #{startTime}, #{updateStartTime}, #{startDayDiff}, #{stationNo})")
    boolean insertStationTrain(StationTrain stationTrain);

    /**
     * 更新站点信息
     * @param stationTrain 需要更新车辆到站信息
     * @return 更新结果
     */
    @Update("UPDATE station_train " +
            "SET arrive_day_diff = #{arriveDayDiff}, arrive_time = #{arriveTime}, " +
            "update_arrive_time = #{updateArriveTime}, start_time = #{startTime}, " +
            "update_start_time = #{updateStartTime}, start_day_diff = #{startDayDiff} " +
            "WHERE station_telecode = #{stationTelecode} AND train_code = #{trainCode}")
    boolean updateStationTrain(StationTrain stationTrain);

    /**
     * 更新列车到站信息,变更列车到站
     * @param trainCode 列车号
     * @param stationTelecode 当前车站号
     * @param updateStationTrainTelecode 要更新的车站号
     * @return 是否成功
     */
    @Update("UPDATE station_train " +
            "SET station_telecode = #{updateStationTrainTelecode} " +
            "WHERE station_telecode = #{trainCode} AND station_telecode = #{stationTelecode}")
    boolean updateStationTrainTelecode(String trainCode, String stationTelecode, String updateStationTrainTelecode);

    @Select("SELECT station_no FROM station_train WHERE train_code = #{trainCode} ORDER BY station_no")
    List<Integer> selectStationNos(String trainCode);


    @Delete("DELETE FROM station_train WHERE train_code = #{stationTrainCode} AND station_telecode = #{stationTelecode}")
    Integer deleteStationTrainByKey(String stationTrainCode, String stationTelecode);
}
