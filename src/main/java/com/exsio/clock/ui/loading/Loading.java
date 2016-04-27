package com.exsio.clock.ui.loading;


import com.exsio.clock.ui.UI;

public final class Loading {

    private static boolean disposed;

    private final static LoadingFramePresenter loading = new LoadingFramePresenter();

    private Loading() {
    }

    public static void show() {
        if (UI.isAvailable()) {
            check();
            loading.show();
        }
    }

    public static void dispose() {
        if (UI.isAvailable()) {
            check();
            loading.dispose();
            disposed = true;
        }
    }

    private static void check() {
        if (disposed) {
            throw new IllegalStateException("Loading screen has already been disposed");
        }
    }

    public static void setDisposed(boolean disposed) {
        Loading.disposed = disposed;
    }
}
