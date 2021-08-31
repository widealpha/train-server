package cn.widealpha.train.service;

import cn.widealpha.train.dao.OrderFormMapper;
import cn.widealpha.train.dao.StationMapper;
import cn.widealpha.train.dao.TickerMapper;
import cn.widealpha.train.domain.OrderForm;
import cn.widealpha.train.domain.Station;
import cn.widealpha.train.domain.Ticket;
import cn.widealpha.train.util.UserUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderFormService {
    @Autowired
    OrderFormMapper orderFormMapper;
    @Autowired
    TickerMapper tickerMapper;
    @Autowired
    StationMapper stationMapper;

    public List<OrderForm> userOrderForms() {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        return orderFormMapper.selectOrderFormByUserId(UserUtil.getCurrentUserId());
    }

    public boolean payOrderForm(int orderId) throws AlipayApiException {
        OrderForm orderForm = orderFormMapper.selectOrderFormByOrderId(orderId);
        Ticket ticket = tickerMapper.selectTicketByOrderFormId(orderId);
        List<Station> stationNames = stationMapper.selectStationsByTelecode(Arrays.asList(ticket.getStartStationTelecode(), ticket.getEndStationTelecode()));
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016072200101XXXX", "请复制第1步中生成的密钥中的商户应用私钥", "json", "utf-8", "沙箱环境RSA2支付宝公钥", "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);
        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setTotalAmount(orderForm.getPrice().toString());
        model.setSubject(ticket.getStationTrainCode() + ":" + stationNames.get(0).getName() + "->" + stationNames.get(1).getName());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.print(response.getBody());
        System.out.print(response.getQrCode());
        return true;
    }
}
