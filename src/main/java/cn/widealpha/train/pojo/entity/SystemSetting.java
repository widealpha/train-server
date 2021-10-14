package cn.widealpha.train.pojo.entity;


public class SystemSetting {

  private Integer id;
  private Integer start;
  private java.sql.Time updateTime;
  private Integer maxTransferCalculate;
  private boolean hasAliPayKey;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }


  public java.sql.Time getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Time updateTime) {
    this.updateTime = updateTime;
  }


  public Integer getMaxTransferCalculate() {
    return maxTransferCalculate;
  }

  public void setMaxTransferCalculate(Integer maxTransferCalculate) {
    this.maxTransferCalculate = maxTransferCalculate;
  }

  public boolean isHasAliPayKey() {
    return hasAliPayKey;
  }

  public void setHasAliPayKey(boolean hasAliPayKey) {
    this.hasAliPayKey = hasAliPayKey;
  }
}
