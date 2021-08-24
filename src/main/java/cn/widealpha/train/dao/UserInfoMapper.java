package cn.widealpha.train.dao;

import cn.widealpha.train.domain.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectByUserId(Integer userId);

    @Insert("INSERT INTO user_info (user_id, real_name, head_image, nickname)" +
            " VALUES (#{userId}, #{username}, #{headImage}, #{nickname})")
    boolean insertUserInfo(UserInfo userInfo);
}
