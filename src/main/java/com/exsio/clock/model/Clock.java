package com.exsio.clock.model;


public class Clock {

    private final String TIME_SEPARATOR = ":";
    private final String SPACE = "&nbsp;";
    private final String MINUS = "-";

    private int minutes;
    private int seconds;

    private boolean alert;

    public Clock(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public boolean isAlert() {
        return alert;
    }

    public void tick() {
        if (alert || (minutes == 0 && seconds == 0)) {
            alert = true;
            forward();
        } else {
            rewind();
        }
    }

    private void rewind() {
        seconds--;
        if (seconds < 0) {
            minutes--;
            seconds = 59;
        }
    }

    private void forward() {
        seconds++;
        if (seconds >= 60) {
            minutes++;
            seconds = 0;
        }
    }

    @Override
    public String toString() {
        String prefix = alert ? MINUS : SPACE;
        return prefix + formatWithLeadingZero(minutes) + TIME_SEPARATOR + formatWithLeadingZero(seconds);
    }

    private String formatWithLeadingZero(int subject) {
        return subject > 9 ? Integer.toString(subject) : "0" + Integer.toString(subject);
    }
}
