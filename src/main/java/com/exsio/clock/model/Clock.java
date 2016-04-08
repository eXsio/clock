package com.exsio.clock.model;

public class Clock {

    private Time time = new Time();
    private Time boundary = new Time();

    private boolean alert;

    public Clock() {
    }

    public boolean isAlert() {
        return alert;
    }

    public void tick() {
        time.forward();
        alert = time.compareTo(boundary) == 1;
    }

    public void reset() {
        time = new Time();
        alert = false;
    }

    public void setBoundary(Time boundary) {
        this.boundary = boundary;
    }

    public Time getBoundary() {
        return boundary;
    }

    public String getTime() {
        return time.toString();
    }

    @Override
    public String toString() {
        return getTime();
    }

}
