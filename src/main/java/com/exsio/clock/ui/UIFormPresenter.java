package com.exsio.clock.ui;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.TimeInfoModel;
import com.exsio.clock.service.clock.ClockService;
import com.exsio.clock.util.SpringProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@Profile(SpringProfile.UI)
class UIFormPresenter {

    private final ClockService clockService;

    private final UIFormView view;

    private boolean started = false;

    @Autowired
    public UIFormPresenter(ClockService clockService) {
        this.clockService = clockService;
        this.view = new UIFormView(this);
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

    void setClicked(int minutes, int seconds) {
        clockService.set(minutes, seconds);
    }

    void resetClicked() {
        clockService.reset();
    }

    @EventListener(TimeChangedEvent.class)
    public void onTimeChanged(TimeChangedEvent event) {
        final TimeInfoModel timeInfo = event.getObject();
        Color color = Color.BLACK;
        if (timeInfo.isAlert()) {
            color = Color.RED;
        }
        view.setTime(timeInfo.getTime(), color, timeInfo.isClockStarted());
        started = timeInfo.isClockStarted();
    }

    public UIFormView getView() {
        return view;
    }
}
