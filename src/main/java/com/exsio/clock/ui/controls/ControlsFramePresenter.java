package com.exsio.clock.ui.controls;

import com.exsio.clock.ui.loading.Loading;
import com.exsio.clock.util.SpringProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
@Profile(SpringProfile.UI)
class ControlsFramePresenter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControlsFramePresenter.class);

    private final ControlsFrameView view;

    private final ControlsFormPresenter formPresenter;

    @Autowired
    public ControlsFramePresenter(ControlsFormPresenter formPresenter) {
        this.view = new ControlsFrameView();
        this.formPresenter = formPresenter;
    }

    ControlsFramePresenter(ControlsFrameView view, ControlsFormPresenter formPresenter) {
        this.view = view;
        this.formPresenter = formPresenter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart(ApplicationReadyEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                view.add(formPresenter.getView(), BorderLayout.CENTER);
                view.pack();

                Loading.dispose();

                view.showOnScreen(0);
                view.setVisible(true);
                LOGGER.info("Application UI started successfully");
            }
        });
    }
}
