package com.exsio.clock.ui;

import java.awt.*;

public class UI {

    private final static String ICON_PATH = "/static/icons/launcher-icon-2x.png";

    private static Boolean available;

    public static Image getIcon() {
        return Toolkit.getDefaultToolkit().getImage(UI.class.getResource(ICON_PATH));
    }

    public static boolean isAvailable() {
        if(available == null) {
            available = !GraphicsEnvironment.isHeadless();
        }
        return available;
    }
}
