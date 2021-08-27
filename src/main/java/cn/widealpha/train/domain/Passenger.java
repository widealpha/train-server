package cn.widealpha.train.domain;

public class Passenger {

  private long passengerId;
  private String idCardNo;
  private long student;


  public long getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(long passengerId) {
    this.passengerId = passengerId;
  }


  public String getIdCardNo() {
    return idCardNo;
  }

  public void setIdCardNo(String idCardNo) {
    this.idCardNo = idCardNo;
  }


  public long getStudent() {
    return student;
  }

  public void setStudent(long student) {
    this.student = student;
  }

}
