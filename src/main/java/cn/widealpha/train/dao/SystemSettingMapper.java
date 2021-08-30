package cn.widealpha.train.dao;

import cn.widealpha.train.domain.SystemSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SystemSettingMapper {
    @Select("SELECT * FROM `system`")
    SystemSetting selectSystemSetting();

    @Update("UPDATE `system` SET start = #{start} AND update_time = #{updateTime} " +
            "AND max_transfer_calculate = #{maxTransferCalculate} WHERE id = 1")
    Integer updateSystemSetting(SystemSetting systemSetting);
}
