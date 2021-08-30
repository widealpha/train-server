package cn.widealpha.train.domain;

import java.util.Date;

public class TrainTicketRemain {
    private String startStationTelecode;
    private String endStationTelecode;
    private Integer remaining;
    private String stationTrainCode;
    private String trainClassCode;
    private String trainClassName;
    private String seatTypeCode;
    private String seatTypeName;
    private Date date;

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

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
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

    public String getTrainClassCode() {
        return trainClassCode;
    }

    public void setTrainClassCode(String trainClassCode) {
        this.trainClassCode = trainClassCode;
    }

    public String getTrainClassName() {
        return trainClassName;
    }

    public void setTrainClassName(String trainClassName) {
        this.trainClassName = trainClassName;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
