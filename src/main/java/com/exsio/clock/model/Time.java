package com.exsio.clock.model;

public class Time implements Comparable<Time> {

    private final String TIME_SEPARATOR = ":";
    private final String ZERO = "0";

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
}
