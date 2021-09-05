package cn.widealpha.train.domain;

import java.util.List;

public class Train {

  private String trainNo;
  private String stationTrainCode;
  private String startStationTelecode;
  private java.sql.Time startStartTime;
  private String endStationTelecode;
  private java.sql.Time endArriveTime;
  private String trainTypeCode;
  private String trainClassCode;
  private String seatTypes;
  private java.sql.Date startDate;
  private java.sql.Date stopDate;
  private List<StationTrain> trainStations;
  private String nowStartStationTelecode;
  private String nowEndStationTelecode;
  private List<TrainPrice> trainPrices;
  private List<TrainTicketRemain> trainTicketRemains;

  public String getTrainNo() {
    return trainNo;
  }

  public void setTrainNo(String trainNo) {
    this.trainNo = trainNo;
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


  public java.sql.Time getStartStartTime() {
    return startStartTime;
  }

  public void setStartStartTime(java.sql.Time startStartTime) {
    this.startStartTime = startStartTime;
  }


  public String getEndStationTelecode() {
    return endStationTelecode;
  }

  public void setEndStationTelecode(String endStationTelecode) {
    this.endStationTelecode = endStationTelecode;
  }


  public java.sql.Time getEndArriveTime() {
    return endArriveTime;
  }

  public void setEndArriveTime(java.sql.Time endArriveTime) {
    this.endArriveTime = endArriveTime;
  }


  public String getTrainTypeCode() {
    return trainTypeCode;
  }

  public void setTrainTypeCode(String trainTypeCode) {
    this.trainTypeCode = trainTypeCode;
  }


  public String getTrainClassCode() {
    return trainClassCode;
  }

  public void setTrainClassCode(String trainClassCode) {
    this.trainClassCode = trainClassCode;
  }


  public String getSeatTypes() {
    return seatTypes;
  }

  public void setSeatTypes(String seatTypes) {
    this.seatTypes = seatTypes;
  }


  public java.sql.Date getStartDate() {
    return startDate;
  }

  public void setStartDate(java.sql.Date startDate) {
    this.startDate = startDate;
  }


  public java.sql.Date getStopDate() {
    return stopDate;
  }

  public void setStopDate(java.sql.Date stopDate) {
    this.stopDate = stopDate;
  }

  public List<StationTrain> getTrainStations() {
    return trainStations;
  }

  public void setTrainStations(List<StationTrain> trainStations) {
    this.trainStations = trainStations;
  }

  public String getNowStartStationTelecode() {
    return nowStartStationTelecode;
  }

  public void setNowStartStationTelecode(String nowStartStationTelecode) {
    this.nowStartStationTelecode = nowStartStationTelecode;
  }

  public String getNowEndStationTelecode() {
    return nowEndStationTelecode;
  }

  public void setNowEndStationTelecode(String nowEndStationTelecode) {
    this.nowEndStationTelecode = nowEndStationTelecode;
  }

  public List<TrainPrice> getTrainPrices() {
    return trainPrices;
  }

  public void setTrainPrices(List<TrainPrice> trainPrices) {
    this.trainPrices = trainPrices;
  }

  public List<TrainTicketRemain> getTrainTicketRemains() {
    return trainTicketRemains;
  }

  public void setTrainTicketRemains(List<TrainTicketRemain> trainTicketRemains) {
    this.trainTicketRemains = trainTicketRemains;
  }
}
