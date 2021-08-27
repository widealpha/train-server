package cn.widealpha.train.domain;

public class UserTicket {

  private long userId;
  private long ticketId;
  private long pay;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getTicketId() {
    return ticketId;
  }

  public void setTicketId(long ticketId) {
    this.ticketId = ticketId;
  }


  public long getPay() {
    return pay;
  }

  public void setPay(long pay) {
    this.pay = pay;
  }

}
