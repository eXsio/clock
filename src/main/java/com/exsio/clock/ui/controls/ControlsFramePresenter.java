package com.exsio.clock.ui.controls;

import com.exsio.clock.ui.loading.LoadingFramePresenter;
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
public class ControlsFramePresenter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControlsFramePresenter.class);

    private final ControlsFrameView view;

    private final ControlsFormPresenter formPresenter;

    private final LoadingFramePresenter loadingFramePresenter;

    @Autowired
    public ControlsFramePresenter(ControlsFormPresenter formPresenter, LoadingFramePresenter loadingFramePresenter) {
        this.view = new ControlsFrameView();
        this.formPresenter = formPresenter;
        this.loadingFramePresenter = loadingFramePresenter;
    }

    ControlsFramePresenter(ControlsFrameView view, ControlsFormPresenter formPresenter,
                           LoadingFramePresenter loadingFramePresenter) {
        this.view = view;
        this.formPresenter = formPresenter;
        this.loadingFramePresenter = loadingFramePresenter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart(ApplicationReadyEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                view.add(formPresenter.getView(), BorderLayout.CENTER);
                view.pack();

                loadingFramePresenter.dispose();

                view.showOnScreen(0);
                view.setVisible(true);
                LOGGER.info("Application UI started successfully");
            }
        });
    }
}
