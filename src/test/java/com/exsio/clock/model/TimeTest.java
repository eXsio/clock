package com.exsio.clock.model;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TimeTest {

    @Test
    public void test_toString() {
        assertEquals(new Time().toString(),"00:00");
        assertEquals(new Time(9,59).toString(),"09:59");
        assertEquals(new Time(10,1).toString(),"10:01");
    }

    @Test
    public void test_forward() {
        Time time = new Time(0,58);
        time.forward();
        assertEquals(time.toString(),"00:59");
        time.forward();
        assertEquals(time.toString(),"01:00");
        time.forward();
        assertEquals(time.toString(),"01:01");
    }

    @Test
    public void test_compareTo() {
        assertEquals(new Time(1, 0).compareTo(new Time(0, 59)), 1);
        assertEquals(new Time(0,59).compareTo(new Time(0, 58)),1);
        assertEquals(new Time(0, 59).compareTo(new Time(1, 0)), -1);
        assertEquals(new Time(0,58).compareTo(new Time(0, 59)),-1);
        assertEquals(new Time(0, 59).compareTo(new Time(0, 59)), 0);
        assertEquals(new Time(1,0).compareTo(new Time(1, 0)),0);
    }


    @Test
    public void test_fullCircle() {

        Time time = new Time(999,58);
        time.forward();
        assertEquals(time.toString(),"999:59");
        time.forward();
        assertEquals(time.toString(), "00:00");
        time.forward();
        assertEquals(time.toString(), "00:01");
    }
}
