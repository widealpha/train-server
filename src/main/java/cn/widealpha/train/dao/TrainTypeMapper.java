package cn.widealpha.train.dao;

import cn.widealpha.train.domain.TrainType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TrainTypeMapper {
    @Select("SELECT count(*) FROM train_type WHERE train_type_code = #{trainTypeCode}")
    boolean existTrainTypeCode(String trainTypeCode);

    @Select("SELECT * FROM train_type WHERE train_type_code = #{trainTypeCode}")
    TrainType selectTrainTypeByTrainCode(String trainTypeCode);

    @Select("SELECT * FROM train_type")
    List<TrainType> selectAllTrainType();

    @Insert("INSERT IGNORE INTO train_type " +
            "(train_type_code, train_type_name) VALUES (#{trainTypeCode}, #{trainTypeName})")
    Boolean addTrainType(TrainType trainType);
    
    @Update("UPDATE train_type SET train_type_name = #{trainTypeName} WHERE train_type_code = #{trainTypeCode}")
    Boolean updateTrainTypeName(TrainType trainType);
}
