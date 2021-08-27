package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Station;
import cn.widealpha.train.util.MybatisExtendedLanguageDriver;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StationMapper {
    @Select("SELECT telecode from station WHERE telecode = #{telecode}")
    String selectStationNameByTelecode(String telecode);

    @Select("SELECT * FROM same_station WHERE origin = #{telecode}")
    List<Station> selectSameStationTelecode(String telecode);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT * FROM station WHERE telecode IN #{telecodeList}")
    List<Station> selectStationsByTelecode(List<String> telecodeList);
}
