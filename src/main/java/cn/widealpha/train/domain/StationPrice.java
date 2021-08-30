package cn.widealpha.train.domain;


public class StationPrice {

  private String startStationTelecode;
  private String endStationTelecode;
  private Double price;
  private String trainClassCode;
  private String seatTypeCode;


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


  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }


  public String getTrainClassCode() {
    return trainClassCode;
  }

  public void setTrainClassCode(String trainClassCode) {
    this.trainClassCode = trainClassCode;
  }


  public String getSeatTypeCode() {
    return seatTypeCode;
  }

  public void setSeatTypeCode(String seatTypeCode) {
    this.seatTypeCode = seatTypeCode;
  }

}
