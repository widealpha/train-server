package cn.widealpha.train.domain;

public class Passenger {

  private Integer passengerId;
  private String idCardNo;
  private Boolean student;


  public Integer getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(Integer passengerId) {
    this.passengerId = passengerId;
  }


  public String getIdCardNo() {
    return idCardNo;
  }

  public void setIdCardNo(String idCardNo) {
    this.idCardNo = idCardNo;
  }

  public Boolean getStudent() {
    return student;
  }

  public void setStudent(Boolean student) {
    this.student = student;
  }
}
