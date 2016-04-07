package com.exsio.clock.ui.controls;

import com.exsio.clock.ui.loading.LoadingFramePresenter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;

import java.awt.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ControlsFramePresenterTest {

    ControlsFramePresenter underTest;

    @Mock
    ControlsFrameView view;

    @Mock
    ControlsFormPresenter formPresenter;

    @Mock
    ControlsFormView formView;

    @Mock
    LoadingFramePresenter loadingPresenter;

    @Mock
    ApplicationReadyEvent applicationReadyEvent;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(formPresenter.getView()).thenReturn(formView);
        underTest = new ControlsFramePresenter(view, formPresenter, loadingPresenter);
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
        verify(loadingPresenter).dispose();
        verify(formPresenter).getView();

    }
}
