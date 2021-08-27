package cn.widealpha.train.domain;


public class System {

  private long id;
  private long start;
  private java.sql.Time updateTime;
  private long maxTransferCalculate;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }


  public java.sql.Time getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Time updateTime) {
    this.updateTime = updateTime;
  }


  public long getMaxTransferCalculate() {
    return maxTransferCalculate;
  }

  public void setMaxTransferCalculate(long maxTransferCalculate) {
    this.maxTransferCalculate = maxTransferCalculate;
  }

}
