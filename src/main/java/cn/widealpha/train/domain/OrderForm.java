package cn.widealpha.train.domain;

public class OrderForm {

  private Integer orderId;
  private Integer userId;
  private Integer ticketId;
  private Boolean payed;


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


  public Integer getTicketId() {
    return ticketId;
  }

  public void setTicketId(Integer ticketId) {
    this.ticketId = ticketId;
  }

  public Boolean getPayed() {
    return payed;
  }

  public void setPayed(Boolean payed) {
    this.payed = payed;
  }
}
