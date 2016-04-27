package com.exsio.clock.ui;


import com.exsio.clock.AbstractDisplayAwareTest;
import com.exsio.clock.ui.loading.Loading;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class ErrorHandlerTest extends AbstractDisplayAwareTest {

    private ErrorHandler underTest = new ErrorHandler() {

        @Override
        protected void exit() {
            exitCalled = true;
        }
    };

    private boolean exitCalled;

    @Test
    public void test_onApplicationError() {

        Loading.setDisposed(false);
        ApplicationFailedEvent event = mock(ApplicationFailedEvent.class);
        when(event.getException()).thenReturn(new RuntimeException());

        underTest.onApplicationError(event);

        assertTrue(exitCalled);
    }
}
