package cn.widealpha.train.pojo.entity;

import cn.widealpha.train.pojo.bo.TrainPrice;
import cn.widealpha.train.pojo.bo.TrainTicketRemain;

import java.util.List;
import java.util.Objects;

public class Train {

  private String trainCode;
  private String trainNo;
  private String startStationTelecode;
  private java.sql.Time startStartTime;
  private String endStationTelecode;
  private java.sql.Time endArriveTime;
  private String trainTypeCode;
  private String trainClassCode;
  private String seatTypes;
  private java.sql.Date startDate;
  private java.sql.Date stopDate;
  private List<StationTrain> stationTrains;
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


  public String getTrainCode() {
    return trainCode;
  }

  public void setTrainCode(String trainCode) {
    this.trainCode = trainCode;
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
    return stationTrains;
  }

  public void setTrainStations(List<StationTrain> stationTrains) {
    this.stationTrains = stationTrains;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Train train = (Train) o;
    return Objects.equals(trainNo, train.trainNo) && Objects.equals(trainCode, train.trainCode) && Objects.equals(startStationTelecode, train.startStationTelecode) && Objects.equals(startStartTime, train.startStartTime) && Objects.equals(endStationTelecode, train.endStationTelecode) && Objects.equals(endArriveTime, train.endArriveTime) && Objects.equals(trainTypeCode, train.trainTypeCode) && Objects.equals(trainClassCode, train.trainClassCode) && Objects.equals(seatTypes, train.seatTypes) && Objects.equals(startDate, train.startDate) && Objects.equals(stopDate, train.stopDate) && Objects.equals(stationTrains, train.stationTrains) && Objects.equals(nowStartStationTelecode, train.nowStartStationTelecode) && Objects.equals(nowEndStationTelecode, train.nowEndStationTelecode) && Objects.equals(trainPrices, train.trainPrices) && Objects.equals(trainTicketRemains, train.trainTicketRemains);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trainNo, trainCode, startStationTelecode, startStartTime, endStationTelecode, endArriveTime, trainTypeCode, trainClassCode, seatTypes, startDate, stopDate, stationTrains, nowStartStationTelecode, nowEndStationTelecode, trainPrices, trainTicketRemains);
  }
}
