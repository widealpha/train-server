package cn.widealpha.train.domain;

public class Ticket {

  private long ticketId;
  private long coachId;
  private long seat;
  private String stationTrainCode;
  private String startStationTelecode;
  private String endStationTelecode;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private double price;


  public long getTicketId() {
    return ticketId;
  }

  public void setTicketId(long ticketId) {
    this.ticketId = ticketId;
  }


  public long getCoachId() {
    return coachId;
  }

  public void setCoachId(long coachId) {
    this.coachId = coachId;
  }


  public long getSeat() {
    return seat;
  }

  public void setSeat(long seat) {
    this.seat = seat;
  }


  public String getStationTrainCode() {
    return stationTrainCode;
  }

  public void setStationTrainCode(String stationTrainCode) {
    this.stationTrainCode = stationTrainCode;
  }


  public String getStartStationTelecode() {
    return startStationTelecode;
  }

  public void setStartStationTelecode(String startStationTelecode) {
    this.startStationTelecode = startStationTelecode;
  }


  public String getEndStationTelecode() {
    return endStationTelecode;
  }

  public void setEndStationTelecode(String endStationTelecode) {
    this.endStationTelecode = endStationTelecode;
  }


  public java.sql.Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Timestamp startTime) {
    this.startTime = startTime;
  }


  public java.sql.Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(java.sql.Timestamp endTime) {
    this.endTime = endTime;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
