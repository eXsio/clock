package com.exsio.clock.model;

import com.exsio.clock.exception.TimeOverflowRuntimeException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Time implements Comparable<Time> {

    public final static int LIMIT = 1000;
    public final static int SECOND = 1000;
    public final static int SECONDS_IN_MINUTE = 60;
    public final static int MINUTE = SECONDS_IN_MINUTE * SECOND;

    private long milliseconds;

    public Time() {
        this(0, 0);
    }

    public Time(int minutes, int seconds) {
        milliseconds = TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
        if (milliseconds >= LIMIT * MINUTE) {
            throw new TimeOverflowRuntimeException(formatTime(milliseconds), formatTime(LIMIT * MINUTE));
        }
    }

    void forward(long milliseconds) {
        this.milliseconds += milliseconds;
        if (this.milliseconds >= LIMIT * MINUTE) {
            this.milliseconds = 0;
        }
    }

    @Override
    public String toString() {
        return formatTime(milliseconds);
    }

    private String formatTime(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % SECONDS_IN_MINUTE);
    }

    @Override
    public int compareTo(Time o) {
        return new Long(milliseconds).compareTo(o.milliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return milliseconds == time.milliseconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(milliseconds);
    }
}
