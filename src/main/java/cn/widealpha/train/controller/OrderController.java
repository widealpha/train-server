package cn.widealpha.train.controller;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.domain.OrderForm;
import cn.widealpha.train.service.OrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orderForm")
public class OrderController {
    @Autowired
    OrderFormService orderFormService;

    @RequestMapping("myOrderForms")
    ResultEntity myOrderForms(){
        return ResultEntity.data(orderFormService.userOrderForms());
    }
}
