package com.exsio.clock.ui;

import javax.swing.*;
import java.awt.*;

public class ScreenAwareFrame extends JFrame {

    public ScreenAwareFrame() {
    }

    public final void showOnScreen(int screen) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        Rectangle r = null;
        if (screen > -1 && screen < gd.length) {
            r = gd[screen].getDefaultConfiguration().getBounds();
        } else if (gd.length > 0) {
            r = gd[0].getDefaultConfiguration().getBounds();
        } else {
            throw new RuntimeException("No Screens Found");
        }
        this.setLocation(this.getDisplayX(r), this.getDisplayY(r));
    }

    private int getDisplayX(Rectangle r) {
        return r.x + (r.width / 2) - (this.getWidth() / 2);
    }

    private int getDisplayY(Rectangle r) {
        return r.y + (r.height / 2) - (this.getHeight() / 2);
    }
}
