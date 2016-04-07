package com.exsio.clock.controller;

import com.exsio.clock.service.clock.ClockService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ClockControllerTest {

    private ClockController underTest;

    @Mock
    private ClockService clockService;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new ClockController(clockService);
    }

    @BeforeMethod
    public void beforeMethod() {
        Mockito.reset(clockService);
    }

    @Test
    public void test_set() {
        underTest.set(0,0);
        verify(clockService).set(0, 0);
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_reset() {
        underTest.reset();
        verify(clockService).reset();
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_start() {
        underTest.start();
        verify(clockService).start();
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_stop() {
        underTest.stop();
        verify(clockService).stop();
        verifyNoMoreInteractions(clockService);
    }
}
