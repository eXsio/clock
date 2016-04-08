package com.exsio.clock.ui.controls;


import com.exsio.clock.ui.ScreenAwareFrame;
import com.exsio.clock.util.Icon;

public class ControlsFrameView extends ScreenAwareFrame {

    public ControlsFrameView() {
        setTitle("Zegar");
        setVisible(false);
        setResizable(false);
        setIconImage(Icon.get());
    }

}
