package cn.widealpha.train.pojo.bo;

import cn.widealpha.train.pojo.entity.Train;

import java.sql.Time;

public class ChangeTrain {
    private String firstTrainCode;
    private Time firstTrainArriveTime;
    private String lastTrainCode;
    private Time lastTrainStartTime;
    private String changeStation;
    private Integer interval;
    private Train firstTrain;
    private Train lastTrain;
    private Integer length;

    public String getFirstTrainCode() {
        return firstTrainCode;
    }

    public void setFirstTrainCode(String firstTrainCode) {
        this.firstTrainCode = firstTrainCode;
    }

    public String getLastTrainCode() {
        return lastTrainCode;
    }

    public void setLastTrainCode(String lastTrainCode) {
        this.lastTrainCode = lastTrainCode;
    }

    public String getChangeStation() {
        return changeStation;
    }

    public void setChangeStation(String changeStation) {
        this.changeStation = changeStation;
    }

    public Time getFirstTrainArriveTime() {
        return firstTrainArriveTime;
    }

    public void setFirstTrainArriveTime(Time firstTrainArriveTime) {
        this.firstTrainArriveTime = firstTrainArriveTime;
    }

    public Time getLastTrainStartTime() {
        return lastTrainStartTime;
    }

    public void setLastTrainStartTime(Time lastTrainStartTime) {
        this.lastTrainStartTime = lastTrainStartTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Train getFirstTrain() {
        return firstTrain;
    }

    public void setFirstTrain(Train firstTrain) {
        this.firstTrain = firstTrain;
    }

    public Train getLastTrain() {
        return lastTrain;
    }

    public void setLastTrain(Train lastTrain) {
        this.lastTrain = lastTrain;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
