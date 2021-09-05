package cn.widealpha.train.service;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.config.AlipayConfig;
import cn.widealpha.train.dao.OrderFormMapper;
import cn.widealpha.train.dao.StationMapper;
import cn.widealpha.train.dao.TicketMapper;
import cn.widealpha.train.domain.OrderForm;
import cn.widealpha.train.domain.Station;
import cn.widealpha.train.domain.Ticket;
import cn.widealpha.train.util.UserUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OrderFormService {
    @Autowired
    OrderFormMapper orderFormMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    StationMapper stationMapper;

    public OrderForm addOrderForm(List<Integer> ticketIds){
        if (UserUtil.getCurrentUserId() != null){
            List<Ticket> tickets = new ArrayList<>();
            double price = 0;
            for (Integer ticketId : ticketIds){
                Ticket ticket = ticketMapper.selectTicketByTicketId(ticketId);
                if (ticket == null){
                    continue;
                }
                tickets.add(ticket);
                price += ticket.getPrice();
            }
            OrderForm orderForm = new OrderForm();
            orderForm.setUserId(UserUtil.getCurrentUserId());
            orderForm.setPrice(price);
            orderFormMapper.insertOrderForm(orderForm);
            orderForm = orderFormMapper.selectOrderFormByOrderId(orderForm.getOrderId());
            for (Ticket ticket : tickets){
                ticketMapper.insertTicketOrderLink(ticket.getTicketId(), orderForm.getOrderId());
            }
            return orderForm;
        }
        return null;
    }

    public List<OrderForm> userOrderForms() {
        if (UserUtil.getCurrentUserId() == null) {
            return new ArrayList<>();
        }
        return orderFormMapper.selectOrderFormByUserId(UserUtil.getCurrentUserId());
    }

    public ResultEntity payOrderForm(int orderId) throws AlipayApiException {
        OrderForm orderForm = orderFormMapper.selectOrderFormByOrderId(orderId);
        if (orderForm == null) {
            return ResultEntity.error(StatusCode.NO_DATA_EXIST);
        }
        if (orderForm.getPayed() == 1) {
            return ResultEntity.error(StatusCode.BEEN_PAYED);
        }
        if (orderForm.getPayed() == 2) {
            return ResultEntity.error(StatusCode.PAY_CANCELED);
        }
        List<Ticket> tickets = ticketMapper.selectTicketByOrderFormId(orderId);
        if (tickets.isEmpty()) {
            return ResultEntity.error(StatusCode.NO_DATA_EXIST);
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.certAlipayRequest());
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.notify_url);
        model.setOutTradeNo(String.valueOf(orderId));
        model.setTotalAmount(orderForm.getPrice().toString());
        StringBuilder s = new StringBuilder();
        for (Ticket ticket : tickets) {
            List<Station> stationNames = stationMapper.selectStationsByTelecode(Arrays.asList(ticket.getStartStationTelecode(), ticket.getEndStationTelecode()));
            s.append(ticket.getStationTrainCode()).append(":").append(stationNames.get(0).getName()).append("->").append(stationNames.get(1).getName());
            s.append('\n');
        }
        model.setSubject(s.toString());
        AlipayTradePrecreateResponse response = alipayClient.certificateExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            return ResultEntity.data(response.getQrCode());
        } else {
            System.out.println("调用失败");
            return ResultEntity.error(StatusCode.PAY_FAILED);
        }
    }

    public StatusCode cancelOrderForm(int orderId) throws AlipayApiException {
        OrderForm orderForm = orderFormMapper.selectOrderFormByOrderId(orderId);
        if (orderForm == null) {
            return StatusCode.NO_DATA_EXIST;
        }
        if (orderForm.getPayed() == 0) {
            return StatusCode.PAY_NEED;
        }
        if (orderForm.getPayed() == 2) {
            return StatusCode.PAY_CANCELED;
        }
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.certAlipayRequest());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        request.setBizModel(model);
        model.setOutTradeNo(orderId + "");
        model.setRefundAmount(orderForm.getPrice() + "");
        model.setRefundReason("退票");
        AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
        if (response.isSuccess()) {
            orderForm.setPayed(2);
            orderFormMapper.updateOrderForm(orderForm);
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.COMMON_FAIL;
        }
    }

    public ResultEntity orderFormStatus(int orderId){
        OrderForm orderForm =orderFormMapper.selectOrderFormByOrderId(orderId);
        if (orderForm.getUserId().equals(UserUtil.getCurrentUserId())){
            return ResultEntity.data(orderForm.getPayed());
        }
        return ResultEntity.error(StatusCode.NO_PERMISSION);
    }

    @Transactional
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String s : requestParams.keySet()) {
            String[] values = requestParams.get(s);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(s, valueStr);
        }
        System.out.println(request.getParameterMap());
        boolean signVerified = AlipaySignature.rsaCertCheckV1(params, AlipayConfig.alipay_cert_path, AlipayConfig.charset, AlipayConfig.sign_type);
        System.out.println(signVerified);
        if (signVerified) {
            //商户订单号
            String formId = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //用户id
            String money = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            System.out.println(tradeStatus);
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                OrderForm orderForm = orderFormMapper.selectOrderFormByOrderId(Integer.parseInt(formId));
                orderForm.setPayed(1);
                orderFormMapper.updateOrderForm(orderForm);
            }
        }
    }
}
