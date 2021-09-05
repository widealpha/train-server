package cn.widealpha.train.domain;

import java.sql.Timestamp;

public class OrderForm {

    private Integer orderId;
    private Integer userId;
    private Integer payed;
    private Double price;
    private Timestamp time;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
