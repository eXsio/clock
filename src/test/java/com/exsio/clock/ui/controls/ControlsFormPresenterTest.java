package com.exsio.clock.ui.controls;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.clock.ClockService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertEquals;

public class ControlsFormPresenterTest {

    private ControlsFormPresenter underTest;

    @Mock
    private ControlsFormView view;

    @Mock
    private ClockService clockService;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new ControlsFormPresenter(clockService, view);
    }

    @BeforeMethod
    public void beforeMethod() {
        Mockito.reset(clockService, view);
    }

    @Test
    public void test_starStopClickedAndTimeChanged() {
        underTest.starStopClicked();
        verify(clockService).start();
        verify(view).setStarted(true);

        underTest.onTimeChanged(new TimeChangedEvent(new TimeInfo("00:01", false, true)));
        verify(view).setTime("00:01", Color.BLACK, true);

        underTest.onTimeChanged(new TimeChangedEvent(new TimeInfo("-00:01", true, true)));
        verify(view).setTime("-00:01", Color.RED, true);

        underTest.starStopClicked();
        verify(clockService).stop();
        verify(view).setStarted(false);

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_setClicked() {
        underTest.setClicked(0, 0);
        verify(clockService).set(0, 0);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_resetClicked() {
        underTest.resetClicked();
        verify(clockService).reset();
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(clockService);
    }

    @Test
    public void test_getView() {
        ControlsFormView result = underTest.getView();
        assertEquals(result, view);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(clockService);
    }
}