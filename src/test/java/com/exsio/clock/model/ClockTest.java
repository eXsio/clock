package com.exsio.clock.model;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ClockTest {

    @Test
    public void test_getTime() {
        assertTrue(new Clock().getTime().equals("00:00"));
    }

    @Test
    public void test_stepUp() {
        Clock clock = new Clock();
        clock.setBoundary(new Time(0,2));
        clock.tick();
        assertEquals(clock.getTime(), "00:01");
    }

    @Test
    public void test_stepWithEqualBoundary() {
        Clock clock = new Clock();
        clock.setBoundary(new Time(0,1));
        clock.tick();
        assertEquals(clock.getTime(), "00:01");
        assertFalse(clock.isAlert());
    }

    @Test
    public void test_stepAboveBoundary() {
        Clock clock = new Clock();
        clock.setBoundary(new Time(0,1));
        clock.tick();
        clock.tick();

        assertEquals(clock.getTime(), "00:02");
        assertTrue(clock.isAlert());

    }
}
