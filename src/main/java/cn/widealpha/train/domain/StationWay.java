package cn.widealpha.train.domain;


import java.math.BigInteger;

public class StationWay {

  private String startStationTelecode;
  private String endStationTelecode;
  private Integer coachId;
  private BigInteger seat;


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

  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }


  public BigInteger getSeat() {
    return seat;
  }

  public void setSeat(BigInteger seat) {
    this.seat = seat;
  }
}
