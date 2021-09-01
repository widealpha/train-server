package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.domain.OrderForm;
import cn.widealpha.train.service.OrderFormService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("orderForm")
public class OrderController {
    @Autowired
    OrderFormService orderFormService;

    @RequestMapping("myOrderForms")
    ResultEntity myOrderForms(){
        return ResultEntity.data(orderFormService.userOrderForms());
    }

    @RequestMapping("payOrderForm")
    ResultEntity payOrderForm(@RequestParam int orderId) throws AlipayApiException {
        return orderFormService.payOrderForm(orderId);
    }

    @RequestMapping("cancelOrderForm")
    ResultEntity cancelOrderForm(@RequestParam int orderId) throws AlipayApiException {
        return ResultEntity.data(orderFormService.cancelOrderForm(orderId));
    }

    @RequestMapping ("/notify")
    public void payNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderFormService.payNotify(request,response);
    }
}
