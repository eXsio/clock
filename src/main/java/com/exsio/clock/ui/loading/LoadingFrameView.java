package com.exsio.clock.ui.loading;

import com.exsio.clock.ui.ScreenAwareFrame;
import com.exsio.clock.ui.UI;

import javax.swing.*;
import java.awt.*;

public class LoadingFrameView extends ScreenAwareFrame {

    public LoadingFrameView() {

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Uruchamianie aplikacji Zegar...");
        progressBar.setStringPainted(true);
        progressBar.setSize(new Dimension(300, 30));
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panel);

        setUndecorated(true);
        setTitle("Zegar - uruchamianie");

        pack();
        setResizable(false);
        setIconImage(UI.getIcon());
    }
}
