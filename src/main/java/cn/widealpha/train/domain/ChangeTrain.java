package cn.widealpha.train.domain;

import java.sql.Time;

public class ChangeTrain {
    private String firstStationTrainCode;
    private Time firstTrainArriveTime;
    private String lastStationTrainCode;
    private Time lastTrainStartTime;
    private String changeStation;
    private Integer interval;

    public String getFirstStationTrainCode() {
        return firstStationTrainCode;
    }

    public void setFirstStationTrainCode(String firstStationTrainCode) {
        this.firstStationTrainCode = firstStationTrainCode;
    }

    public String getLastStationTrainCode() {
        return lastStationTrainCode;
    }

    public void setLastStationTrainCode(String lastStationTrainCode) {
        this.lastStationTrainCode = lastStationTrainCode;
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
}
