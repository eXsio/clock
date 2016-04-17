package com.exsio.clock.model;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Time implements Comparable<Time> {

    public final static int LIMIT = 1000;
    public final static int SECOND = 1000;
    public final static int MINUTE = 60 * SECOND;

    private final static String TIME_SEPARATOR = ":";
    private final static String ZERO = "0";

    private final static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    private long miliseconds;

    public Time() {
        this(0, 0);
    }

    public Time(int minutes, int seconds) {
        this.miliseconds =
                TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
    }

    void forward(long miliseconds) {
        this.miliseconds +=miliseconds;
        if(this.miliseconds == LIMIT * MINUTE) {
            this.miliseconds = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s:%s",
                formatWithLeadingZero(TimeUnit.MILLISECONDS.toMinutes(miliseconds)),
                formatWithLeadingZero(TimeUnit.MILLISECONDS.toSeconds(miliseconds) % 60));
    }

    private String formatWithLeadingZero(long subject) {
        return subject > 9 ? Long.toString(subject) : ZERO + Long.toString(subject);
    }

    @Override
    public int compareTo(Time o) {
        return new Long(miliseconds).compareTo(o.miliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return miliseconds == time.miliseconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(miliseconds);
    }
}
