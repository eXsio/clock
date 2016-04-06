package com.exsio.clock.ui;

import com.exsio.clock.util.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile(SpringProfile.UI)
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
