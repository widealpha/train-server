package cn.widealpha.train.domain;

public class Passenger {

  private Integer passengerId;
  private String idCardNo;
  private Boolean student;
  private Boolean verified;
  private Boolean studentVerified;
  private String name;
  private String phone;


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

  public Boolean getVerified() {
    return verified;
  }

  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  public Boolean getStudentVerified() {
    return studentVerified;
  }

  public void setStudentVerified(Boolean studentVerified) {
    this.studentVerified = studentVerified;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
