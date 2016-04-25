package com.exsio.clock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingDialogUtils {

    public static JDialog waitForDialog(String title) {

        int loops = 0;
        JDialog win = null;
        do {
            for (Window window : Frame.getWindows()) {
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    if (title.equals(dialog.getTitle())) {
                        win = dialog;
                        break;
                    }
                }
            }

            if (win == null) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    break;
                }
            }
            loops++;
            if(loops > 10) {
                throw new RuntimeException("no dialog found");
            }
        } while (win == null);

        return win;

    }

    public static JButton getButton(Container container, String text) {
        JButton btn = null;
        List<Container> children = new ArrayList<>(25);
        for (Component child : container.getComponents()) {
            if (child instanceof JButton) {
                JButton button = (JButton) child;
                if (text.equals(button.getText())) {
                    btn = button;
                    break;
                }
            } else if (child instanceof Container) {
                children.add((Container) child);
            }
        }
        if (btn == null) {
            for (Container cont : children) {
                JButton button = getButton(cont, text);
                if (button != null) {
                    btn = button;
                    break;
                }
            }
        }
        return btn;
    }
}
