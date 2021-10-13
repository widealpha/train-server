package cn.widealpha.train.dao;

import cn.widealpha.train.domain.TrainClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TrainClassMapper {
    @Select("SELECT count(*) FROM train_class WHERE train_class_code = #{trainClassCode}")
    boolean existTrainClassCode(String trainClassCode);

    @Select("SELECT * FROM train_class WHERE train_class_code = #{trainClassCode}")
    TrainClass selectTrainClassByClassCode(String trainClassCode);

    @Select("SELECT * FROM train_class")
    List<TrainClass> selectAllTrainClass();

    @Insert("INSERT IGNORE INTO train_class " +
            "(train_class_code, train_class_name) VALUES (#{trainClassCode}, #{trainClassName})")
    Boolean insertTrainClass(TrainClass trainClass);

    @Update("UPDATE train_class SET train_class_name = #{trainClassName} WHERE train_class_code = #{trainClassCode}")
    Boolean updateTrainClassName(TrainClass trainClass);
}
