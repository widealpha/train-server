package cn.widealpha.train.pojo.entity;

import java.math.BigInteger;

public class Coach {

  private Integer coachId;
  private Integer coachNo;
  private String trainCode;
  private String seatTypeCode;
  private BigInteger seat;
  private Integer seatCount;


  public Integer getCoachId() {
    return coachId;
  }

  public void setCoachId(Integer coachId) {
    this.coachId = coachId;
  }


  public Integer getCoachNo() {
    return coachNo;
  }

  public void setCoachNo(Integer coachNo) {
    this.coachNo = coachNo;
  }


  public String getTrainCode() {
    return trainCode;
  }

  public void setTrainCode(String trainCode) {
    this.trainCode = trainCode;
  }


  public String getSeatTypeCode() {
    return seatTypeCode;
  }

  public void setSeatTypeCode(String seatTypeCode) {
    this.seatTypeCode = seatTypeCode;
  }

  public BigInteger getSeat() {
    return seat;
  }

  public void setSeat(BigInteger seat) {
    this.seat = seat;
  }

  public Integer getSeatCount() {
    return seatCount;
  }

  public void setSeatCount(Integer seatCount) {
    this.seatCount = seatCount;
  }
}
