package com.exsio.clock.ui.loading;

import com.exsio.clock.AbstractDisplayAwareTest;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

public class LoadingTest extends AbstractDisplayAwareTest {

    @Test
    public void test_showDispose() throws InterruptedException {

        Loading.setDisposed(false);

        Loading.show();
        Thread.sleep(100);
        Loading.dispose();
    }

    @Test
    public void test_construct() throws Exception {
        Constructor constructor = Loading.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
