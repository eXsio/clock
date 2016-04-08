package com.exsio.clock.ui.loading;

import com.exsio.clock.ui.ScreenAwareFrame;
import com.exsio.clock.util.Icon;

import javax.swing.*;
import java.awt.*;

public class LoadingFrameView extends ScreenAwareFrame {

    public LoadingFrameView() {

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Uruchamianie aplikacji...");
        progressBar.setSize(new Dimension(200, 10));
        add(progressBar);

        setUndecorated(true);
        setTitle("Zegar - uruchamianie");

        pack();
        setResizable(false);
        setIconImage(Icon.get());
        showOnScreen(0);
        setVisible(true);
    }
}
