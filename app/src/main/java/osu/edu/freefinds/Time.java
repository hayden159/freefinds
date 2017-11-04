package osu.edu.freefinds;

import java.io.Serializable;

/**
 * Created by Liz on 11/3/2017.
 */

public class Time implements Serializable {
    private int hour;
    private int minute;

    public Time(){
        this.hour = 0;
        this.minute = 0;
    }

    public Time(int hourParam, int minuteParam){
        this.hour = hourParam;
        this.minute = minuteParam;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
