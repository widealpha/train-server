package cn.widealpha.train.pojo.bo;

import java.sql.Time;

public class TrainStationTime {
    private String trainCode;
    private Time time;
    //距离出发日期几天到达
    private int day;

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
