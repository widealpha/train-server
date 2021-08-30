package cn.widealpha.train.domain;

public class StationTrain {

  private String stationTrainCode;
  private String stationTelecode;
  private Integer arriveDayDiff;
  private java.sql.Time arriveTime;
  private java.sql.Time updateArriveTime;
  private java.sql.Time startTime;
  private java.sql.Time updateStartTime;
  private Integer startDayDiff;
  private Integer stationNo;


  public String getStationTrainCode() {
    return stationTrainCode;
  }

  public void setStationTrainCode(String stationTrainCode) {
    this.stationTrainCode = stationTrainCode;
  }


  public String getStationTelecode() {
    return stationTelecode;
  }

  public void setStationTelecode(String stationTelecode) {
    this.stationTelecode = stationTelecode;
  }


  public Integer getArriveDayDiff() {
    return arriveDayDiff;
  }

  public void setArriveDayDiff(Integer arriveDayDiff) {
    this.arriveDayDiff = arriveDayDiff;
  }


  public java.sql.Time getArriveTime() {
    return arriveTime;
  }

  public void setArriveTime(java.sql.Time arriveTime) {
    this.arriveTime = arriveTime;
  }


  public java.sql.Time getUpdateArriveTime() {
    return updateArriveTime;
  }

  public void setUpdateArriveTime(java.sql.Time updateArriveTime) {
    this.updateArriveTime = updateArriveTime;
  }


  public java.sql.Time getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Time startTime) {
    this.startTime = startTime;
  }


  public java.sql.Time getUpdateStartTime() {
    return updateStartTime;
  }

  public void setUpdateStartTime(java.sql.Time updateStartTime) {
    this.updateStartTime = updateStartTime;
  }


  public Integer getStartDayDiff() {
    return startDayDiff;
  }

  public void setStartDayDiff(Integer startDayDiff) {
    this.startDayDiff = startDayDiff;
  }


  public Integer getStationNo() {
    return stationNo;
  }

  public void setStationNo(Integer stationNo) {
    this.stationNo = stationNo;
  }

}
