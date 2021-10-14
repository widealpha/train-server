package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.Station;
import cn.widealpha.train.util.MybatisExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationMapper {
    @Select("SELECT * from station WHERE telecode = #{telecode}")
    Station selectStationNameByTelecode(String telecode);

    @Select("SELECT same FROM same_station WHERE origin = #{telecode}")
    List<String> selectSameStationTelecode(String telecode);

    @Select("SELECT * FROM station")
    List<Station> selectAllStations();

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM station WHERE telecode IN (#{telecodeList})")
    List<Station> selectStationsByTelecode(List<String> telecodeList);

    @Options(useGeneratedKeys = true, keyProperty = "telecode")
    @Insert("INSERT INTO station (name, telecode, en, abbr) VALUES (name, telecode, en, abbr)")
    boolean insertStation(Station station);
}
