package com.exsio.clock.model;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ClockTest {

    @Test
    public void test_getTime() {
        assertTrue(new Clock(0, 0).getTime().equals(" 00:00"));
    }

    @Test
    public void test_stepDownBelowMinute() {
        Clock clock = new Clock(1, 0);
        clock.tick();
        assertEquals(clock.getTime(), " 00:59");
    }

    @Test
    public void test_stepDownMinute() {
        Clock clock = new Clock(1, 1);
        clock.tick();
        assertEquals(clock.getTime(), " 01:00");
    }

    @Test
    public void test_stepDownZero() {
        Clock clock = new Clock(0, 1);
        clock.tick();
        assertEquals(clock.getTime(), " 00:00");
    }

    @Test
    public void test_stepDownBelowZero() {
        Clock clock = new Clock(0, 0);
        clock.tick();
        assertEquals(clock.getTime(), "-00:01");
    }

    @Test
    public void test_stepDownMinuteBelowZero() {
        Clock clock = new Clock(0, 0);
        for(int i =0; i<61; i++) {
            clock.tick();
        }
        assertEquals(clock.getTime(), "-01:01");
    }
}
