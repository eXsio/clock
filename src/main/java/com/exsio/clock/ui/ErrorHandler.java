package com.exsio.clock.ui;

import com.exsio.clock.main.Application;
import com.exsio.clock.ui.loading.Loading;
import com.exsio.clock.util.SpringProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
@Profile(SpringProfile.UI)
class ErrorHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @EventListener(ApplicationFailedEvent.class)
    public void onApplicationError(ApplicationFailedEvent event) {
        Loading.dispose();
        Throwable t = event.getException();
        LOGGER.error("{}: {}", t.getClass().getName(), t.getMessage(), t);
        exit();
    }

    protected void exit() {
        Application.exit();
    }

}
