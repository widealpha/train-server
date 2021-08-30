package cn.widealpha.train.dao;

import cn.widealpha.train.domain.OrderForm;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderFormMapper {
    @Select("SELECT * FROM order_form WHERE user_id = #{userId}")
    List<OrderForm> selectOrderFormByUserId(int userId);
}
