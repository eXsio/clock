package com.exsio.clock.ui.controls;


import com.exsio.clock.ui.ScreenAwareFrame;
import com.exsio.clock.util.Icon;

class ControlsFrameView extends ScreenAwareFrame {

    ControlsFrameView() {
        setTitle("Zegar");
        setVisible(false);
        setResizable(false);
        setIconImage(Icon.get());
    }

}
