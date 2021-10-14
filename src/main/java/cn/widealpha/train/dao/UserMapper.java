package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT username FROM user WHERE id = #{userId}")
    String selectUsernameById(int userId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO user (username, password) VALUES (#{username}, #{password})")
    boolean insert(User user);
}
