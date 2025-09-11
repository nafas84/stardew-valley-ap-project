package io.Ap.StardewValley.Common.Model.Time;

import io.Ap.StardewValley.Client.Controller.NetworkControllers.UpdateController;
import io.Ap.StardewValley.Common.Model.App;

public class DateAndTime {
    private int hour; // (9-24):00
    private int minute;
    private int day;
    private Weather weather;

    private float accumulatedTime = 0f;

    public DateAndTime(int hour, int day, Weather weather) {
        this.hour = hour;
        this.day = day;
        this.weather = weather;
        this.minute = 0;
    }

    public void update(float delta) {
        accumulatedTime += delta;

        while (accumulatedTime >= 1f) {
            minute += 1;
            accumulatedTime -= 1f;

            if (minute >= 60) {
                minute = 0;
                hour++;
            }
        }
    }

    public String getFormattedTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void addHour(int hour) {
        this.hour += hour;
    }

    public void addDay(int day) {
        this.day += day;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
        this.minute = 0;
    }

    public Weather getWeather() {
        return weather;
    }

    public Season getSeason() {
        int s = ((this.day - 1) / 28) % 4;
        return Season.values()[s];
    }

    public WeekDay getDayOfWeek() {
        int w = (this.day - 1) % 7;
        return WeekDay.values()[w];
    }

    public int getMinute() {
        return minute;
    }

    public float getAccumulatedTime() {
        return accumulatedTime;
    }
}
