package com.exsio.clock.ui.controls;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.clock.ClockService;
import com.exsio.clock.ui.task.UITaskExecutor;
import com.exsio.clock.util.SpringProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@Profile(SpringProfile.UI)
class ControlsFormPresenter {

    final static Color ERROR_COLOR = new Color(220, 20, 60);
    final static Color NORMAL_COLOR = Color.BLACK;

    private final ClockService clockService;

    private final ControlsFormView view;

    private boolean started = false;

    @Autowired
    public ControlsFormPresenter(ClockService clockService, UITaskExecutor uiTaskExecutor) {
        this.clockService = clockService;
        this.view = new ControlsFormView(this, uiTaskExecutor);
    }

    ControlsFormPresenter(ClockService clockService, ControlsFormView view) {
        this.clockService = clockService;
        this.view = view;
    }

    void starStopClicked() {
        if (started) {
            clockService.stop();
            view.setStarted(false);
        } else {
            clockService.start();
            view.setStarted(true);
        }
    }

    void init() {
        view.init();
    }

    void setClicked(int minutes, int seconds) {
        clockService.set(minutes, seconds);
    }

    void resetClicked() {
        clockService.reset();
    }

    @EventListener(TimeChangedEvent.class)
    public void onTimeChanged(TimeChangedEvent event) {
        final TimeInfo timeInfo = event.getObject();
        Color color = NORMAL_COLOR;
        if (timeInfo.isAlert()) {
            color = ERROR_COLOR;
        }
        view.setTime(timeInfo.getTime(), timeInfo.getBoundary(), color, timeInfo.isClockStarted());
        started = timeInfo.isClockStarted();
    }

    public ControlsFormView getView() {
        return view;
    }

    public boolean isClockStarted() {
        return started;
    }
}
