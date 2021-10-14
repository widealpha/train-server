package cn.widealpha.train.dao;

import cn.widealpha.train.pojo.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT role FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{id})")
    List<String> selectRolesByUserId(Integer userId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO role SET role = #{role}")
    boolean createRole(Role role);

    @Select("SELECT * FROM user_role WHERE role_id = #{roleId}")
    Role selectRoleByRoleId(Integer roleId);

    @Select("SELECT id from role WHERE role = #{role}")
    Integer selectRoleIdByRoleName(String role);

    @Insert("INSERT INTO user_role VALUES (#{userId}, #{roleId})")
    boolean insertRoleUserLink(Integer userId, Integer roleId);

    @Insert("DELETE FROM user_role WHERE user_id = #{userId} AND role_id = #{roleId}")
    boolean deleteUserRoleLink(Integer userId, Integer roleId);

    @Select("SELECT user_id FROM user_role WHERE role_id = #{roleId}")
    List<Integer> selectUserIdWithRoleId(int roleId);
}
