package cn.widealpha.train.domain;

public class Coach {

  private long coachId;
  private long coachNo;
  private String stationTrainCode;
  private String seatTypeCode;
  private  long seat;


  public long getCoachId() {
    return coachId;
  }

  public void setCoachId(long coachId) {
    this.coachId = coachId;
  }


  public long getCoachNo() {
    return coachNo;
  }

  public void setCoachNo(long coachNo) {
    this.coachNo = coachNo;
  }


  public String getStationTrainCode() {
    return stationTrainCode;
  }

  public void setStationTrainCode(String stationTrainCode) {
    this.stationTrainCode = stationTrainCode;
  }


  public String getSeatTypeCode() {
    return seatTypeCode;
  }

  public void setSeatTypeCode(String seatTypeCode) {
    this.seatTypeCode = seatTypeCode;
  }


  public long getSeat() {
    return seat;
  }

  public void setSeat(long seat) {
    this.seat = seat;
  }

}
