package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.StationPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StationPriceMapper {
    @Select("SELECT * FROM station_price " +
            "WHERE start_station_telecode = #{startStationTelecode} " +
            "AND end_station_telecode = #{endStationTelecode} " +
            "AND train_class_code = #{trainClassCode}")
    List<StationPrice> selectStationPrice(String startStationTelecode, String endStationTelecode, String trainClassCode);

    @Update("UPDATE station_price SET price = price * #{ratio} WHERE seat_type_code = #{seatTypeCode}")
    Integer updateSeatPriceRatio(String seatTypeCode, double ratio);

    @Update("UPDATE station_price SET price = price * #{ratio} WHERE train_class_code = #{trainClassCode}")
    Integer updateTrainClassPriceRatio(String trainClassCode, double ratio);

    @Update("UPDATE station_price SET price = price * #{ratio} WHERE start_station_telecode = #{startStationTelecode} AND end_station_telecode = #{endStationTelecode}")
    Integer updateStationPriceRatio(String startStationTelecode, String endStationTelecode, double ratio);
}
