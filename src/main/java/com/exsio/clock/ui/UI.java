package com.exsio.clock.ui;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;

public final class UI {

    private final static String ICON_PATH = "/static/icons/launcher-icon-2x.png";

    private static Boolean available;

    private static Optional<Desktop> desktop;

    private UI() {
    }

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
        if (isAvailable()) {
            Optional<Desktop> desktop = getDesktop();
            if (desktop.isPresent() && desktop.get().isSupported(Desktop.Action.BROWSE)) {
                desktop.get().browse(uri);
            }
        }
    }

    public static Optional<Desktop> getDesktop() {
        if(desktop == null) {
            desktop = Desktop.isDesktopSupported() ? Optional.of(Desktop.getDesktop()) : Optional.<Desktop>absent();
        }
        return desktop;
    }

    public static void setDesktop(Optional<Desktop> desktop) {
        UI.desktop = desktop;
    }
}
