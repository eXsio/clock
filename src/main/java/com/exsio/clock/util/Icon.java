package com.exsio.clock.util;

import java.awt.*;

public abstract class Icon {

    private final static String ICON_PATH = "/static/icons/launcher-icon-2x.png";

    public static Image get() {
        return Toolkit.getDefaultToolkit().getImage(Icon.class.getResource(ICON_PATH));
    }
}
