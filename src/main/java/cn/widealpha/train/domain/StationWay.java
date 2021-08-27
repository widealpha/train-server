package cn.widealpha.train.domain;


public class StationWay {

  private String startStationTelecode;
  private String endStationTelecode;
  private String stationTrainCode;
  private long coachId;
  private long seat;


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


  public String getStationTrainCode() {
    return stationTrainCode;
  }

  public void setStationTrainCode(String stationTrainCode) {
    this.stationTrainCode = stationTrainCode;
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

}
