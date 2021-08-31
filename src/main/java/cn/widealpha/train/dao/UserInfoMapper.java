package cn.widealpha.train.dao;

import cn.widealpha.train.domain.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectByUserId(Integer userId);

    @Insert("INSERT INTO user_info (user_id, head_image, nickname)" +
            " VALUES (#{userId}, #{headImage}, #{nickname})")
    boolean insertUserInfo(UserInfo userInfo);

    @Update("UPDATE user_info " +
            "SET real_name = #{realName}, gender = #{gender}, phone = #{phone}, mail = #{mail}, address = #{address}, nickname = #{nickname}, head_image = #{headImage}, self_passenger_id = #{selfPassengerId} " +
            "WHERE user_id = #{userId}")
    boolean updateUserInfo(UserInfo userInfo);
}
