package cn.widealpha.train.domain;

public class OrderForm {

  private Integer orderId;
  private Integer userId;
  private Boolean payed;
  private Double price;


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

  public Boolean getPayed() {
    return payed;
  }

  public void setPayed(Boolean payed) {
    this.payed = payed;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
