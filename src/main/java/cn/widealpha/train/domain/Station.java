package cn.widealpha.train.domain;

public class Station {
  private String name;
  private String telecode;
  private String en;
  private String abbr;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getTelecode() {
    return telecode;
  }

  public void setTelecode(String telecode) {
    this.telecode = telecode;
  }


  public String getEn() {
    return en;
  }

  public void setEn(String en) {
    this.en = en;
  }


  public String getAbbr() {
    return abbr;
  }

  public void setAbbr(String abbr) {
    this.abbr = abbr;
  }

}
