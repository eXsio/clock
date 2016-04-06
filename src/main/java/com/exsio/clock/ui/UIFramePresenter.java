package com.exsio.clock.ui;

import com.exsio.clock.ui.loading.LoadingFramePresenter;
import com.exsio.clock.util.SpringProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
@Profile(SpringProfile.UI)
public class UIFramePresenter {

    private final static Logger LOGGER = LoggerFactory.getLogger(UIFramePresenter.class);

    private final UIFrameView view;

    private final UIFormPresenter formPresenter;

    private final LoadingFramePresenter loadingFramePresenter;

    @Autowired
    public UIFramePresenter(UIFormPresenter formPresenter, LoadingFramePresenter loadingFramePresenter) {
        this.view = new UIFrameView();
        this.formPresenter = formPresenter;
        this.loadingFramePresenter = loadingFramePresenter;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationStart(ContextRefreshedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.setTitle("Zegar");

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
