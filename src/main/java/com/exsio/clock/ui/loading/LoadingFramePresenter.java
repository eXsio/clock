package com.exsio.clock.ui.loading;

import javax.swing.*;

public class LoadingFramePresenter {

    private final LoadingFrameView view;

    public LoadingFramePresenter() {
        view = new LoadingFrameView();
    }

    LoadingFramePresenter(LoadingFrameView view) {
        this.view = view;
    }

    public void show() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                view.showOnScreen(0);
                view.setAlwaysOnTop(true);
                view.setVisible(true);
                return null;
            }
        }.execute();
    }

    public void dispose() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                view.setVisible(false);
                view.dispose();
                return null;
            }
        }.execute();
    }
}
