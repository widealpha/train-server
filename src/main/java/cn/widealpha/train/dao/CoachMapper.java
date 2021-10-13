package cn.widealpha.train.dao;

import cn.widealpha.train.domain.Coach;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CoachMapper {
    @Select("SELECT * FROM coach WHERE coach_id = #{coachId}")
    Coach selectCoachByCoachId(int coachId);

    @Select("SELECT * FROM coach WHERE train_code = #{trainCode}")
    List<Coach> selectCoachByTrainCode(String trainCode);

    @Select("SELECT * FROM coach WHERE train_code = #{trainCode} AND seat_type_code = #{seatTypeCode}")
    List<Coach> selectCoachByTrainCodeAndSeatType(String trainCode, String seatTypeCode);

    @Options(useGeneratedKeys = true, keyProperty = "coachId")
    @Insert("INSERT INTO coach (coach_no, train_code, seat_type_code, seat) VALUES (#{coachNo}, #{trainCode}, #{seatTypeCode}, #{seat})")
    Integer insertCoach(Coach coach);

    @Delete("UPDATE coach SET seat = #{seat}, seat_type_code = #{seatTypeCode} WHERE coach_id = #{coachId}")
    boolean updateCoachByCoachId(int coachId, String seatTypeCode, BigInteger seat);

    @Delete("DELETE FROM coach WHERE coach_id = #{coachId}")
    boolean deleteCoachByCoachId(int coachId);
}
