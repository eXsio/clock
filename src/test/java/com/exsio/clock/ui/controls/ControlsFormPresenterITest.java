package com.exsio.clock.ui.controls;

import com.exsio.clock.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ControlsFormPresenterITest extends AbstractIntegrationTest {

    @Autowired
    private ControlsFormPresenter presenter;

    private ControlsFormView view;

    @BeforeClass
    public void init() {
        super.init();
        view = presenter.getView();
    }

    @Test
    public void test_flow() throws InterruptedException {

        Thread.sleep(1000);
        assertEquals(view.time.getText(), "00:00 / 00:00");
        assertEquals(view.time.getForeground().getRGB(), ControlsFormPresenter.NORMAL_COLOR.getRGB());
        assertFalse(presenter.isClockStarted());

        view.minutes.setSelectedItem(0);
        view.seconds.setSelectedItem(1);

        Thread.sleep(500);

        view.set.doClick();
        Thread.sleep(1000);

        assertEquals(view.time.getText(), "00:00 / 00:01");
        assertEquals(view.time.getForeground().getRGB(), ControlsFormPresenter.NORMAL_COLOR.getRGB());

        view.startStop.doClick();
        Thread.sleep(3000);

        assertEquals(view.time.getForeground().getRGB(), ControlsFormPresenter.ERROR_COLOR.getRGB());
        assertTrue(presenter.isClockStarted());
        Thread.sleep(1000);
        view.startStop.doClick();
        Thread.sleep(1000);

        assertEquals(view.time.getForeground().getRGB(), ControlsFormPresenter.ERROR_COLOR.getRGB());
        assertFalse(presenter.isClockStarted());

        view.reset.doClick();
        Thread.sleep(1000);
        assertEquals(view.time.getText(), "00:00 / 00:01");
        assertEquals(view.time.getForeground().getRGB(), ControlsFormPresenter.NORMAL_COLOR.getRGB());
        assertFalse(presenter.isClockStarted());


    }
}
