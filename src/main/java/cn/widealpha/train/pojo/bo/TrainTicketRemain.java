package cn.widealpha.train.pojo.bo;


public class TrainTicketRemain {
    private String startStationTelecode;
    private String endStationTelecode;
    private Integer remaining;
    private String trainCode;
    private String trainClassCode;
    private String trainClassName;
    private String seatTypeCode;
    private String seatTypeName;
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
