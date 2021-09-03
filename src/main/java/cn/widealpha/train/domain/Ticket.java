package cn.widealpha.train.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;

public class Ticket {

    private Integer ticketId;
    private Integer coachId;
    private BigInteger seat;
    private String stationTrainCode;
    private String startStationTelecode;
    private String endStationTelecode;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    private Double price;
    private Integer passengerId;
    private Integer orderId;
    private Boolean student;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }


    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public BigInteger getSeat() {
        return seat;
    }

    public void setSeat(BigInteger seat) {
        this.seat = seat;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }


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


    public java.sql.Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(java.sql.Timestamp startTime) {
        this.startTime = startTime;
    }


    public java.sql.Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(java.sql.Timestamp endTime) {
        this.endTime = endTime;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {
        this.student = student;
    }
}
