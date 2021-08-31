package cn.widealpha.train.dao;

import cn.widealpha.train.domain.OrderForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderFormMapper {
    @Select("SELECT * FROM order_form WHERE user_id = #{userId}")
    List<OrderForm> selectOrderFormByUserId(int userId);

    @Select("SELECT * FROM order_form WHERE order_id = #{orderId}")
    OrderForm selectOrderFormByOrderId(int orderId);

    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    @Insert("INSERT INTO order_form (user_id, price) VALUES (#{userId}, #{price})")
    Integer insertOrderForm(OrderForm orderForm);

    @Update("UPDATE order_form SET price = #{price}, payed = #{payed} WHERE order_id = #{orderId}")
    Integer updateOrderForm(OrderForm orderForm);

    @Delete("DELETE FROM order_form WHERE order_id = #{orderId}")
    Boolean deleteOrderForm(int order_id);
}
