package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.Train;
import cn.widealpha.train.util.MybatisExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrainMapper {
    @Select("SELECT distinct train_code from train.train")
    List<String> allTrainCode();

    @Select("SELECT * FROM train WHERE train_code = #{trainCode}")
    Train selectTrainByTrainCode(String trainCode);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT distinct * FROM train WHERE train_code IN (#{trainCodes})")
    List<Train> selectTrainsByTrainCodes(List<String> trainCodes);

    @Select("SELECT * FROM train LIMIT #{page}, #{size}")
    List<Train> selectTrains(int page, int size);

    @Select("SELECT * FROM train WHERE train_class_code = #{trainClassCode} LIMIT #{page}, #{size}")
    List<Train> selectTrainsByClassCode(String trainClassCode,int page, int size);

    @Select("SELECT count(*) FROM train")
    Integer count();

    @Update("UPDATE train SET train_no = #{trainNo}, start_station_telecode = #{startStationTelecode}, " +
            "start_start_time = #{startStartTime}, end_station_telecode = #{endStationTelecode}, " +
            "end_arrive_time = #{endArriveTime}, train_type_code = #{trainTypeCode}, " +
            "train_class_code = #{trainClassCode}, seat_types = #{seatTypes}, " +
            "start_date = #{startDate}, stop_date = #{stopDate} " +
            "WHERE train_code = #{trainCode}")
    boolean updateTrainByTrainCode(Train train);

    @Insert("INSERT INTO train " +
            "(train_no, train_code, start_station_telecode, start_start_time, " +
            "end_station_telecode, end_arrive_time, train_type_code, train_class_code, seat_types, start_date, stop_date) " +
            "VALUES (#{trainNo}, #{trainCode}, #{startStationTelecode}, #{startStartTime}, " +
            "#{endStationTelecode}, #{endArriveTime}, #{trainTypeCode}, #{trainClassCode}, #{seatTypes}, #{startDate}, #{stopDate})")
    boolean insertTrain(Train train);

    @Delete("DELETE FROM train WHERE train_code = #{trainCode}")
    boolean deleteTrainByTrainCode(String trainCode);
}
