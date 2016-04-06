package com.exsio.clock.ui;

import com.exsio.clock.util.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile(SpringProfile.UI)
public class LoadingFramePresenter {

    private final LoadingFrameView view;

    public LoadingFramePresenter() {
        view = new LoadingFrameView();
    }

    public void dispose() {
        view.setVisible(false);
        view.dispose();
    }
}
