package com.exsio.clock.ui.controls;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControlsFramePresenterTest {

    ControlsFramePresenter underTest;


    ControlsFrameView view;

    @Mock
    ControlsFormPresenter formPresenter;

    @Mock
    ControlsFormView formView;

    @Mock
    ApplicationReadyEvent applicationReadyEvent;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(formPresenter.getView()).thenReturn(formView);
        view = spy(new ControlsFrameView(mock(ControlsFramePresenter.class)));
        doNothing().when(view).add(Mockito.<Component>any(), anyObject());
        doNothing().when(view).setVisible(anyBoolean());
        underTest = new ControlsFramePresenter(view, formPresenter);
    }

    @Test
    public void test_onApplicationStart() throws InterruptedException {
        underTest.onApplicationStart(applicationReadyEvent);
        Thread.sleep(1000);
        verify(view).setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        verify(view).add(formView, BorderLayout.CENTER);
        verify(view).pack();
        verify(view).showOnScreen(0);
        verify(view).setVisible(true);
        verify(formPresenter).getView();

    }
}
