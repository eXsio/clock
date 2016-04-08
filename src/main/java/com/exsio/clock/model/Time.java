package com.exsio.clock.model;

import java.util.Objects;

public class Time implements Comparable<Time> {

    private final static String TIME_SEPARATOR = ":";
    private final static String ZERO = "0";

    private int minutes;
    private int seconds;

    public Time() {
        this(0,0);
    }

    public Time(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void forward() {
        seconds++;
        if (seconds >= 60) {
            minutes++;
            seconds = 0;
        }
    }

    @Override
    public String toString() {
        return formatWithLeadingZero(minutes) + TIME_SEPARATOR + formatWithLeadingZero(seconds);
    }

    private String formatWithLeadingZero(int subject) {
        return subject > 9 ? Integer.toString(subject) : ZERO + Integer.toString(subject);
    }

    @Override
    public int compareTo(Time o) {
        if(minutes < o.minutes || (minutes == o.minutes && seconds < o.seconds)) {
            return -1;
        } else if(minutes > o.minutes || (minutes == o.minutes && seconds > o.seconds)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return minutes == time.minutes &&
                seconds == time.seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes, seconds);
    }
}
