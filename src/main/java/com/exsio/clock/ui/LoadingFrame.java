package com.exsio.clock.ui;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoadingFrame extends JFrame {

    public LoadingFrame() {

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Uruchamianie aplikacji...");
        progressBar.setSize(new Dimension(200, 10));
        add(progressBar);

        setLocationRelativeTo(null);
        setUndecorated(true);

        pack();
        setVisible(true);
    }
}
