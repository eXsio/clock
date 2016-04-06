package com.exsio.clock.ui;

import com.exsio.clock.util.SpringProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Profile(SpringProfile.UI)
public class UIFrame extends JFrame {

    private final static Logger LOGGER = LoggerFactory.getLogger(UIFrame.class);

    private final UIForm uiForm;

    @Autowired
    public UIFrame(UIForm uiForm) {
        this.uiForm = uiForm;
        this.setVisible(false);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationStart(ContextRefreshedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("Zegar");
                setLocationRelativeTo(null);

                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                add(uiForm, BorderLayout.CENTER);
                pack();

                setVisible(true);
                LOGGER.info("Application UI started successfully");
            }
        });
    }

}
