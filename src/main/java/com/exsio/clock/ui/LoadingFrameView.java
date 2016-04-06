package com.exsio.clock.ui;

import javax.swing.*;
import java.awt.*;

public class LoadingFrameView extends JFrame {

    public LoadingFrameView() {

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Uruchamianie aplikacji...");
        progressBar.setSize(new Dimension(200, 10));
        add(progressBar);

        setLocationRelativeTo(null);
        setUndecorated(true);

        pack();
        setResizable(false);
        setVisible(true);
    }
}
