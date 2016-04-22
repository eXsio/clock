package com.exsio.clock.ui.loading;

import com.exsio.clock.AbstractDisplayAwareTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LoadingFramePresenterTest extends AbstractDisplayAwareTest {

    private LoadingFramePresenter underTest;

    @Mock
    private LoadingFrameView view;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new LoadingFramePresenter(view);
    }

    @Test
    public void test_show() throws InterruptedException {
        underTest.show();
        Thread.sleep(1000);
        verify(view).showOnScreen(0);

    }

    @Test
    public void test_dispose() throws InterruptedException {
        underTest.dispose();
        Thread.sleep(1000);
        verify(view).setVisible(false);
        verify(view).dispose();
        verifyNoMoreInteractions(view);
    }
}
