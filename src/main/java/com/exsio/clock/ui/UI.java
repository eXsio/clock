package com.exsio.clock.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;

public class UI {

    private final static Logger LOGGER = LoggerFactory.getLogger(UI.class);
    private final static String ICON_PATH = "/static/icons/launcher-icon-2x.png";

    private static Boolean available;

    public static Image getIcon() {
        return Toolkit.getDefaultToolkit().getImage(UI.class.getResource(ICON_PATH));
    }

    public static boolean isAvailable() {
        if (available == null) {
            available = !GraphicsEnvironment.isHeadless();
        }
        return available;
    }

    public static void openWebpage(URI uri) throws Exception{
        if (available) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(uri);
            }
        }
    }
}
