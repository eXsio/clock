package com.exsio.clock.ui;

import java.awt.*;

public class UI {

    private static Boolean available;

    public static boolean isAvailable() {
        if(available == null) {
            available = !GraphicsEnvironment.isHeadless();
        }
        return available;
    }
}
